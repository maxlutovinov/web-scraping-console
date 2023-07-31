package app.webscrapingconsole.service;

import app.webscrapingconsole.model.JobFunction;
import java.util.Optional;

public interface JobFunctionService {
    JobFunction save(JobFunction jobFunction);

    Optional<JobFunction> findByTitle(String title);
}
