package app.webscrapingconsole.service.impl;

import static app.webscrapingconsole.util.ConstantsUtil.CONSOLE_DIALOG;

import app.webscrapingconsole.exception.DataProcessingException;
import app.webscrapingconsole.model.Job;
import app.webscrapingconsole.service.ConsoleService;
import app.webscrapingconsole.service.FileWriterService;
import app.webscrapingconsole.service.WebScrapeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ConsoleServiceImpl implements ConsoleService {
    private final WebScrapeService webScrapeService;
    private final FileWriterService fileWriterService;
    @Value("${app.scraping-result}")
    private String scrapingResult;
    @Value("${app.scraping-url}")
    private String scrapingUrl;
    @Value("#{${app.jobFunction-map}}")
    private Map<String, String> jobFunctionMap;

    public ConsoleServiceImpl(WebScrapeService webScrapeService,
                              FileWriterService fileWriterService) {
        this.webScrapeService = webScrapeService;
        this.fileWriterService = fileWriterService;
    }

    @Override
    public void run() {
        Set<String> jobFunctions = jobFunctionMap.keySet();
        log.info(System.lineSeparator() + "\u001B[33m"
                + jobFunctions.stream().sorted().collect(Collectors.joining(System.lineSeparator()))
                + "\u001B[0m" + System.lineSeparator()
                + "Hello! " + CONSOLE_DIALOG);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            List<Job> jobs;
            while (!(input = reader.readLine()).equals("quit")) {
                if (!isJobFunction(input, jobFunctions)) {
                    log.info(System.lineSeparator() + "No such job function");
                } else {
                    jobs = webScrapeService.scrapeJobs(scrapingUrl + "/jobs?filter="
                            + jobFunctionMap.get(input));
                    String jsonJobs = new ObjectMapper().writeValueAsString(jobs);
                    fileWriterService.writeFile(jsonJobs, scrapingResult);
                    log.info(System.lineSeparator()
                            + "Scraping result saved to DB and write to file:"
                            + System.lineSeparator()
                            + Paths.get(scrapingResult).toAbsolutePath());
                }
                log.info(System.lineSeparator() + CONSOLE_DIALOG);
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
