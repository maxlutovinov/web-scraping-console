package app.webscrapingconsole.service.impl;

import static app.webscrapingconsole.util.ConstantsUtil.CONSOLE_DIALOG;
import static app.webscrapingconsole.util.ConstantsUtil.jobFunctionMap;

import app.webscrapingconsole.exception.DataProcessingException;
import app.webscrapingconsole.model.Job;
import app.webscrapingconsole.service.ConsoleService;
import app.webscrapingconsole.service.FileWriterService;
import app.webscrapingconsole.service.WebScrapeService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConsoleServiceImpl implements ConsoleService {
    private final WebScrapeService webScrapeService;
    private final FileWriterService fileWriterService;
    @Value("${web-scraping.result}")
    private String scrapingResult;
    @Value("${web-scraping.url}")
    private String scrapingUrl;

    public ConsoleServiceImpl(WebScrapeService webScrapeService,
                              FileWriterService fileWriterService) {
        this.webScrapeService = webScrapeService;
        this.fileWriterService = fileWriterService;
    }

    @Override
    public void run() {
        Set<String> jobFunctions = jobFunctionMap.keySet();
        jobFunctions.stream().sorted().forEach(System.out::println);
        System.out.println("Hello! " + CONSOLE_DIALOG);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            List<Job> jobs;
            while (!(input = reader.readLine()).equals("quit")) {
                String[] jobFunctionAndJobsNumber = input.split(",");
                int requestedJobsNumber;
                try {
                    requestedJobsNumber = Math.abs(Integer.parseInt(
                            jobFunctionAndJobsNumber[jobFunctionAndJobsNumber.length - 1]));
                } catch (NumberFormatException e) {
                    requestedJobsNumber = 20;
                }
                String jobFunction = jobFunctionAndJobsNumber[0];
                if (!isJobFunction(jobFunction, jobFunctions)) {
                    System.out.println("No such job function");

                } else {
                    jobs = webScrapeService.scrapeJobs(scrapingUrl + "/jobs?filter="
                            + jobFunctionMap.get(jobFunction), requestedJobsNumber);
                    fileWriterService.writeFile(jobs.toString(), scrapingResult);
                    System.out.println("Scraping result saved to DB and write to file:"
                            + System.lineSeparator()
                            + Paths.get(scrapingResult).toAbsolutePath());
                }
                System.out.println(CONSOLE_DIALOG);
            }
            System.exit(0);
        } catch (IOException e) {
            throw new DataProcessingException("Can't read user input data", e);
        }
    }

    private boolean isJobFunction(String inputJobFunction, Set<String> jobFunctions) {
        return jobFunctions.stream().anyMatch(f -> f.equals(inputJobFunction));
    }
}
