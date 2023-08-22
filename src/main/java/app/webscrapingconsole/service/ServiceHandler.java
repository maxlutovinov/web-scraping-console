package app.webscrapingconsole.service;

import java.util.Optional;

public interface ServiceHandler<T> {

    T save(T entity);

    Optional<T> findByTitle(String title);

    boolean isApplicable(Class<?> entityClass);
}
