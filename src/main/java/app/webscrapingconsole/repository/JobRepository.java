package app.webscrapingconsole.repository;

import app.webscrapingconsole.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    void deleteByUrl(String url);
}
