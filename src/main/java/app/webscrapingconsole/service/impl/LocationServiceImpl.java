package app.webscrapingconsole.service.impl;

import app.webscrapingconsole.model.Location;
import app.webscrapingconsole.repository.LocationRepository;
import app.webscrapingconsole.service.LocationService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Optional<Location> findByTitle(String title) {
        return locationRepository.findByTitle(title);
    }

    @Override
    public boolean isApplicable(Class<?> entityClass) {
        return entityClass.equals(Location.class);
    }
}
