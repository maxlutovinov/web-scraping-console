package app.webscrapingconsole.repository;

import app.webscrapingconsole.model.Location;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByTitle(String title);
}
