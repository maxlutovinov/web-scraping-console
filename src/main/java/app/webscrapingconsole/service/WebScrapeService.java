package app.webscrapingconsole.service;

import app.webscrapingconsole.model.Job;
import java.util.List;

public interface WebScrapeService {
    List<Job> scrapeJobs(String url);
}
