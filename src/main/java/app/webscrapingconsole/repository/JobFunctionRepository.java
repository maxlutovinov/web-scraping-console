package app.webscrapingconsole.repository;

import app.webscrapingconsole.model.JobFunction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobFunctionRepository extends JpaRepository<JobFunction, Long> {
    Optional<JobFunction> findByTitle(String title);
}
