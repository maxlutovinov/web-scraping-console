package app.webscrapingconsole.service;

import app.webscrapingconsole.model.Tag;
import java.util.Optional;

public interface TagService {
    Tag save(Tag tag);

    Optional<Tag> findByTitle(String title);
}
