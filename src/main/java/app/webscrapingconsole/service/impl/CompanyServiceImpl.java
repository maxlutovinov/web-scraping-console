package app.webscrapingconsole.service.impl;

import app.webscrapingconsole.model.Company;
import app.webscrapingconsole.repository.CompanyRepository;
import app.webscrapingconsole.service.CompanyService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> findByTitle(String title) {
        return companyRepository.findByTitle(title);
    }

    @Override
    public boolean isApplicable(Class<?> entityClass) {
        return entityClass.equals(Company.class);
    }
}
