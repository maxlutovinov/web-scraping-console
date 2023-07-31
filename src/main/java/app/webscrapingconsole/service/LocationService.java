package app.webscrapingconsole.service;

import app.webscrapingconsole.model.Location;
import java.util.Optional;

public interface LocationService {
    Location save(Location location);

    Optional<Location> findByTitle(String title);
}
