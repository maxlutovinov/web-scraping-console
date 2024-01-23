package app.webscrapingconsole.service.impl;

import static app.webscrapingconsole.util.ConstantsUtil.APPLY_LINK;
import static app.webscrapingconsole.util.ConstantsUtil.COMPANY_LOGO;
import static app.webscrapingconsole.util.ConstantsUtil.DATE_ATTR;
import static app.webscrapingconsole.util.ConstantsUtil.DATE_VAL;
import static app.webscrapingconsole.util.ConstantsUtil.FUNCTIONS_CLASS;
import static app.webscrapingconsole.util.ConstantsUtil.JOB_DESCRIPTION;
import static app.webscrapingconsole.util.ConstantsUtil.JOB_FILTER;
import static app.webscrapingconsole.util.ConstantsUtil.JOB_ITEM;
import static app.webscrapingconsole.util.ConstantsUtil.JOB_LINK;
import static app.webscrapingconsole.util.ConstantsUtil.JOB_TAG;
import static app.webscrapingconsole.util.ConstantsUtil.MAIN_ATTR;

import app.webscrapingconsole.exception.DataProcessingException;
import app.webscrapingconsole.model.Company;
import app.webscrapingconsole.model.Job;
import app.webscrapingconsole.model.JobFunction;
import app.webscrapingconsole.model.Location;
import app.webscrapingconsole.model.Tag;
import app.webscrapingconsole.service.JobService;
import app.webscrapingconsole.service.JsoupDocumentService;
import app.webscrapingconsole.service.ServiceHandler;
import app.webscrapingconsole.service.ServiceHandlerStrategy;
import app.webscrapingconsole.service.WebScrapeService;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WebScrapeServiceImpl implements WebScrapeService {
    private final JobService jobService;
    private final JsoupDocumentService jsoupDocumentService;
    private final ServiceHandlerStrategy serviceHandlerStrategy;
    @Value("${app.scraping-url}")
    private String scrapingUrl;

    public WebScrapeServiceImpl(JobService jobService,
                                JsoupDocumentService jsoupDocumentService,
                                ServiceHandlerStrategy serviceHandlerStrategy) {
        this.jobService = jobService;
        this.jsoupDocumentService = jsoupDocumentService;
        this.serviceHandlerStrategy = serviceHandlerStrategy;
    }

    @Override
    public List<Job> scrapeJobs(String url) {
        Document doc = jsoupDocumentService.getDocument(url);
        Elements jobElements = doc.getElementsByAttributeValue(MAIN_ATTR, JOB_ITEM);
        List<Job> jobs = new ArrayList<>();
        for (Element jobElement : jobElements) {
            Job job = new Job();
            Elements logoElements =
                    jobElement.getElementsByAttributeValue(MAIN_ATTR, COMPANY_LOGO);
            if (!logoElements.isEmpty()) {
                String companyTitle = logoElements.get(0).attr("alt");
                Company company = getEntity(companyTitle, Company.class);
                String logo = logoElements.get(0).attr("src");
                String[] logoParts = logo.split("/");
                logo = logoParts[0] + "//" + logoParts[2] + "/"
                        + logoParts[logoParts.length - 1];
                company.setLogo(logo);
                serviceHandlerStrategy.getHandler(Company.class).save(company);
                job.setCompany(company);
            }
            Elements functionElements =
                    doc.getElementsByAttributeValueContaining(MAIN_ATTR, JOB_FILTER);
            Set<JobFunction> jobFunctions = new HashSet<>();
            if (!functionElements.isEmpty()) {
                for (Element functionElement : functionElements) {
                    String jobFunctionTitle = functionElement.text();
                    jobFunctions.add(getEntity(jobFunctionTitle, JobFunction.class));
                }
            }
            Elements titleElements =
                    jobElement.getElementsByAttributeValue(MAIN_ATTR, JOB_LINK);
            if (!titleElements.isEmpty()) {
                job.setTitle(titleElements.get(0).text());
                String jobUrl = titleElements.get(0).attr("href");
                if (jobUrl.startsWith("/")) {
                    jobUrl = scrapingUrl + jobUrl;
                    Document jobDocument = jsoupDocumentService.getDocument(jobUrl);
                    functionElements = jobDocument.getElementsByClass(FUNCTIONS_CLASS);
                    if (!functionElements.isEmpty()) {
                        String[] divElements = functionElements.get(0).toString()
                                .split(" </div>")[0]
                                .replace("<!-- --> <!-- -->", "")
                                .split(">");
                        String[] jobFunctionTitles = divElements[divElements.length - 1].trim()
                                .split(", ");
                        for (String jobFunctionTitle : jobFunctionTitles) {
                            jobFunctionTitle = jobFunctionTitle.replace("amp;", "");
                            jobFunctions.add(getEntity(jobFunctionTitle, JobFunction.class));
                        }
                    }
                    Elements descriptionElements =
                            jobDocument.getElementsByAttributeValue(MAIN_ATTR, JOB_DESCRIPTION);
                    if (!descriptionElements.isEmpty()) {
                        job.setDescription(descriptionElements.text()
                                .replace("\"", "'")
                                .replace("\\", "-"));
                    }
                    Elements jobAllicationUrlElements = jobDocument.getElementsByClass(APPLY_LINK);
                    if (!jobAllicationUrlElements.isEmpty()) {
                        String jobApplicationUrl = jobAllicationUrlElements.select("a")
                                .attr("href");
                        job.setJobApplicationUrl(jobApplicationUrl);
                    }
                }
                job.setJobUrl(jobUrl);
            }
            job.setJobFunctions(jobFunctions);
            Elements locactionElements = jobElement.select("span");
            if (!locactionElements.isEmpty()) {
                List<Location> locations = new ArrayList<>();
                Set<String> locationTitles = locactionElements.stream()
                        .map(Element::text)
                        .collect(Collectors.toSet());
                for (String locationTitle : locationTitles) {
                    locations.add(getEntity(locationTitle, Location.class));
                }
                job.setLocations(locations);
            }
            Elements dateElements = jobElement.getElementsByAttributeValue(DATE_ATTR, DATE_VAL);
            if (!dateElements.isEmpty()) {
                String postedDate = dateElements.get(0).attr("content");
                LocalDate date = LocalDate.parse(postedDate);
                ZoneId zoneId = ZoneId.systemDefault();
                job.setPostedDate(date.atStartOfDay(zoneId).toEpochSecond());
            }
            Elements tagElements = jobElement.getElementsByAttributeValue(MAIN_ATTR, JOB_TAG);
            if (!tagElements.isEmpty()) {
                List<Tag> tags = new ArrayList<>();
                for (Element tagElement : tagElements) {
                    String tagTitle = tagElement.text();
                    tags.add(getEntity(tagTitle, Tag.class));
                }
                job.setTags(tags);
            }
            jobs.add(job);
        }
        jobService.deleteAllByUrl(jobs);
        jobService.saveAll(jobs);
        return jobs;
    }

    private <T> T getEntity(String title, Class<T> entityClass) {
        try {
            T entity = entityClass.getConstructor().newInstance();
            ServiceHandler<T> serviceHandler = serviceHandlerStrategy.getHandler(entityClass);
            Optional<T> optionalEntity = serviceHandler.findByTitle(title);
            if (optionalEntity.isEmpty()) {
                Field titleField = entityClass.getDeclaredField("title");
                titleField.setAccessible(true);
                titleField.set(entity, title);
                serviceHandler.save(entity);
            } else {
                entity = optionalEntity.get();
            }
            return entity;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                 | NoSuchMethodException | NoSuchFieldException e) {
            throw new DataProcessingException("Can't create " + entityClass.getSimpleName()
                    + " entity", e);
        }
    }
}
