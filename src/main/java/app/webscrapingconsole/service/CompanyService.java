package app.webscrapingconsole.service;

import app.webscrapingconsole.model.Company;
import java.util.Optional;

public interface CompanyService {
    Company save(Company company);

    Optional<Company> findByTitle(String title);

}
