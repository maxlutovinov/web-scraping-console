package app.webscrapingconsole.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Component;

@Component
public class ServiceHandlerStrategy {
    private final List<ServiceHandler<?>> serviceHandlers;

    public ServiceHandlerStrategy(List<ServiceHandler<?>> serviceHandlers) {
        this.serviceHandlers = serviceHandlers;
    }

    @SuppressWarnings("unchecked")
    public <T> ServiceHandler<T> getHandler(Class<T> entityClass) {
        return (ServiceHandler<T>) serviceHandlers.stream()
                .filter(handler -> handler.isApplicable(entityClass))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }
}
