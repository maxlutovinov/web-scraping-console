package app.webscrapingconsole.service.impl;

import static app.webscrapingconsole.util.ConstantsUtil.COMPANY_LINK;
import static app.webscrapingconsole.util.ConstantsUtil.COMPANY_LOGO;
import static app.webscrapingconsole.util.ConstantsUtil.FUNCTIONS_CLASS;
import static app.webscrapingconsole.util.ConstantsUtil.JOB_DESCRIPTION;
import static app.webscrapingconsole.util.ConstantsUtil.JOB_FILTER;
import static app.webscrapingconsole.util.ConstantsUtil.JOB_ITEM;
import static app.webscrapingconsole.util.ConstantsUtil.JOB_LINK;
import static app.webscrapingconsole.util.ConstantsUtil.JOB_TAG;
import static app.webscrapingconsole.util.ConstantsUtil.MAIN_ATTR;
import static app.webscrapingconsole.util.ConstantsUtil.POSTED_DATE;

import app.webscrapingconsole.model.Company;
import app.webscrapingconsole.model.Job;
import app.webscrapingconsole.model.JobFunction;
import app.webscrapingconsole.model.Location;
import app.webscrapingconsole.model.Tag;
import app.webscrapingconsole.service.CompanyService;
import app.webscrapingconsole.service.JobFunctionService;
import app.webscrapingconsole.service.JobService;
import app.webscrapingconsole.service.JsoupDocumentService;
import app.webscrapingconsole.service.LocationService;
import app.webscrapingconsole.service.TagService;
import app.webscrapingconsole.service.WebScrapeService;
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
    private final TagService tagService;
    private final CompanyService companyService;
    private final LocationService locationService;
    private final JobFunctionService jobFunctionService;
    private final JsoupDocumentService jsoupDocumentService;
    @Value("${app.scraping-url}")
    private String scrapingUrl;

    public WebScrapeServiceImpl(JobService jobService,
                                TagService tagService,
                                CompanyService companyService,
                                LocationService locationService,
                                JobFunctionService jobFunctionService,
                                JsoupDocumentService jsoupDocumentService) {
        this.jobService = jobService;
        this.tagService = tagService;
        this.companyService = companyService;
        this.locationService = locationService;
        this.jobFunctionService = jobFunctionService;
        this.jsoupDocumentService = jsoupDocumentService;
    }

    @Override
    public List<Job> scrapeJobs(String url, int requestedJobsNumber) {
        url = getUrlWithFoundJobs(url, requestedJobsNumber);
        Document doc = jsoupDocumentService.getDocument(url);
        Elements jobElements = doc.getElementsByAttributeValue(MAIN_ATTR, JOB_ITEM);
        List<Job> jobs = new ArrayList<>();
        for (Element jobElement : jobElements) {
            Company company = new Company();
            Job job = new Job();
            Elements logoElements =
                    jobElement.getElementsByAttributeValue(MAIN_ATTR, COMPANY_LOGO);
            if (!logoElements.isEmpty()) {
                String companyTitle = logoElements.get(0).attr("alt");
                Optional<Company> companyOptional = companyService.findByTitle(companyTitle);
                if (companyOptional.isEmpty()) {
                    company.setTitle(companyTitle);
                    String logo = logoElements.get(0).attr("src");
                    String[] logoParts = logo.split("/");
                    logo = logoParts[0] + "//" + logoParts[2] + "/"
                            + logoParts[logoParts.length - 1];
                    company.setLogo(logo);
                } else {
                    company = companyOptional.get();
                }
            }

            Elements functionElements =
                    doc.getElementsByAttributeValueContaining(MAIN_ATTR, JOB_FILTER);
            Set<JobFunction> jobFunctions = new HashSet<>();
            if (!functionElements.isEmpty()) {
                for (Element functionElement : functionElements) {
                    String jobFunctionTitle = functionElement.text();
                    jobFunctions.add(getJobFunction(jobFunctionTitle));
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
                    String[] divElements = functionElements.get(0).toString()
                            .split(" </div>")[0]
                            .replace("<!-- --> <!-- -->", "")
                            .split(">");
                    String[] jobFunctionTitles = divElements[divElements.length - 1].trim()
                            .split(", ");
                    for (String jobFunctionTitle : jobFunctionTitles) {
                        jobFunctions.add(getJobFunction(jobFunctionTitle));
                    }

                    Elements descriptionElements =
                            jobDocument.getElementsByAttributeValue(MAIN_ATTR, JOB_DESCRIPTION);
                    if (!descriptionElements.isEmpty()) {
                        job.setDescription(descriptionElements.text()
                                .replace("\"", "'")
                                .replace("\\", "-"));
                    }

                    Elements companyUrlElements = jobDocument.getElementsByClass(COMPANY_LINK);
                    if (!companyUrlElements.isEmpty()) {
                        String companyUrl = companyUrlElements.select("a")
                                .attr("href");
                        company.setUrl(companyUrl);
                    }
                }
                job.setUrl(jobUrl);
            }
            job.setJobFunction(jobFunctions);

            Elements locactionElements = jobElement.select("span");
            if (!locactionElements.isEmpty()) {
                List<Location> locations = new ArrayList<>();
                Set<String> locationTitles = locactionElements.stream()
                        .map(Element::text)
                        .collect(Collectors.toSet());
                for (String locationTitle : locationTitles) {
                    Location location = new Location();
                    Optional<Location> optionalLocation =
                            locationService.findByTitle(locationTitle);
                    if (optionalLocation.isEmpty()) {
                        location.setTitle(locationTitle);
                        locationService.save(location);
                    } else {
                        location = optionalLocation.get();
                    }
                    locations.add(location);
                }
                job.setLocation(locations);
            }

            Elements dateElements = doc.getElementsByClass(POSTED_DATE);
            if (!dateElements.isEmpty()) {
                Elements dateTag = dateElements.get(0).getElementsByTag("meta");
                if (!dateTag.isEmpty()) {
                    LocalDate date = LocalDate.parse(dateTag.toString()
                            .split(" ")[2]
                            .split("\"")[1]);
                    ZoneId zoneId = ZoneId.systemDefault();
                    job.setPostedDate(date.atStartOfDay(zoneId).toEpochSecond());
                }
            }

            Elements tagElements = jobElement.getElementsByAttributeValue(MAIN_ATTR, JOB_TAG);
            if (!tagElements.isEmpty()) {
                List<Tag> tags = new ArrayList<>();
                for (Element tagElement : tagElements) {
                    Tag tag = new Tag();
                    String tagTitle = tagElement.text();
                    Optional<Tag> optionalTag = tagService.findByTitle(tagTitle);
                    if (optionalTag.isEmpty()) {
                        tag.setTitle(tagTitle);
                        tagService.save(tag);
                    } else {
                        tag = optionalTag.get();
                    }
                    tags.add(tag);
                }
                job.setTags(tags);
            }
            companyService.save(company);
            job.setCompany(company);
            jobs.add(job);
        }
        jobService.deleteAllByUrl(jobs);
        jobService.saveAll(jobs);
        jobs.forEach(System.out::println);
        return jobs;
    }

    private String getUrlWithFoundJobs(String url, int requestedJobsNumber) {
        Document doc = jsoupDocumentService.getDocument(url);
        int foundJobs = Integer.parseInt(
                doc.getElementsByTag("b").text()
                        .replace(",", ""));
        int maxPagesNumber = (int) Math.ceil(foundJobs / 20.0);
        int requestedPagesNumber = (int) Math.ceil(requestedJobsNumber / 20.0);
        requestedPagesNumber = Math.min(requestedPagesNumber, maxPagesNumber);
        url = url + "&page=" + requestedPagesNumber;
        return url;
    }

    private JobFunction getJobFunction(String jobFunctionTitle) {
        JobFunction jobFunction = new JobFunction();
        Optional<JobFunction> optionalJobFunction =
                jobFunctionService.findByTitle(jobFunctionTitle);
        if (optionalJobFunction.isEmpty()) {
            jobFunction.setTitle(jobFunctionTitle);
            jobFunctionService.save(jobFunction);
        } else {
            jobFunction = optionalJobFunction.get();
        }
        return jobFunction;
    }
}
