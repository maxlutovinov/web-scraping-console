package app.webscrapingconsole.service;

import app.webscrapingconsole.model.Job;
import java.util.List;

public interface JobService {
    List<Job> saveAll(List<Job> job);

    void deleteAllByUrl(List<Job> jobs);
}
