package app.webscrapingconsole.service.impl;

import app.webscrapingconsole.model.Tag;
import app.webscrapingconsole.repository.TagRepository;
import app.webscrapingconsole.service.TagService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Optional<Tag> findByTitle(String title) {
        return tagRepository.findByTitle(title);
    }

    @Override
    public boolean isApplicable(Class<?> entityClass) {
        return entityClass.equals(Tag.class);
    }
}
