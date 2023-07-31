package app.webscrapingconsole.service.impl;

import app.webscrapingconsole.model.Job;
import app.webscrapingconsole.repository.JobRepository;
import app.webscrapingconsole.service.JobService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> saveAll(List<Job> job) {
        return jobRepository.saveAll(job);
    }

    @Transactional
    @Override
    public void deleteAllByUrl(List<Job> jobs) {
        for (Job job : jobs) {
            jobRepository.deleteByUrl(job.getUrl());
        }
    }
}
