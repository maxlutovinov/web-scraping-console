package app.webscrapingconsole.service.impl;

import app.webscrapingconsole.model.JobFunction;
import app.webscrapingconsole.repository.JobFunctionRepository;
import app.webscrapingconsole.service.JobFunctionService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class JobFunctionServiceImpl implements JobFunctionService {
    private final JobFunctionRepository jobFunctionRepository;

    public JobFunctionServiceImpl(JobFunctionRepository jobFunctionRepository) {
        this.jobFunctionRepository = jobFunctionRepository;
    }

    @Override
    public JobFunction save(JobFunction jobFunction) {
        return jobFunctionRepository.save(jobFunction);
    }

    @Override
    public Optional<JobFunction> findByTitle(String title) {
        return jobFunctionRepository.findByTitle(title);
    }

    @Override
    public boolean isApplicable(Class<?> entityClass) {
        return entityClass.equals(JobFunction.class);
    }
}
