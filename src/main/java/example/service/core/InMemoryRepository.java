package example.service.core;

import example.model.ElectronicComponent;
import example.service.component_generator.RandomComponentFactory;
import io.micronaut.context.annotation.Value;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * An in-memory-only implementation of {@link ElectronicComponentRepository}. It automatically assigns unique
 * sequential IDs to components
 */
@Singleton
public class InMemoryRepository implements ElectronicComponentRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryRepository.class);

    private final RandomComponentFactory componentFactory;

    // data
    private final Integer numItems;
    private final Map<Long, ElectronicComponent> componentMap;
    private final List<ElectronicComponent> componentList;

    private final AtomicLong idSequence;

    @Inject
    public InMemoryRepository(final RandomComponentFactory componentFactory,
                              @Value("${component-catalog.num-random-items:100}") final Integer numItems) {

        this.componentFactory = componentFactory;
        this.numItems = numItems;

        componentMap = new LinkedHashMap<>(numItems);
        componentList = new ArrayList<>(numItems);
        idSequence = new AtomicLong(0);
    }

    // @PostConstruct called after all DI / value injection is completed
    @PostConstruct
    public void init() {
        populateComponentList();
    }

    private void populateComponentList() {
        
        for (int i = 0; i < numItems; i++) {
            final ElectronicComponent component = componentFactory.makeComponent();

            // set ID
            component.setId(idSequence.getAndIncrement());

            componentList.add(component);
            componentMap.put(component.getId(), component);
        }
        log.info("Populated repository with {} randomly generated items!", numItems);
    }

    @Override
    public ElectronicComponent find(final Long id) {
        return componentMap.get(id);
    }

    @Override
    public Page<ElectronicComponent> find(final Pageable pageable) {

        int pageSize = pageable.getSize();
        int currentPage = pageable.getNumber();
        int startItem = currentPage * pageSize;
        List<ElectronicComponent> list;

        if (componentList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, componentList.size());
            list = componentList.subList(startItem, toIndex);
        }

        return Page.of(list,
                Pageable.from(currentPage, pageSize),
                componentList.size());
    }

    @Override
    public List<ElectronicComponent> findAll() {
        return Collections.unmodifiableList(componentList);
    }

    @Override
    public ElectronicComponent save(final ElectronicComponent component) {

        // assign an ID if messing
        if (component.getId() == null) {
            component.setId(idSequence.getAndIncrement());
        }

        final ElectronicComponent previous = componentMap.get(component.getId());
        if (previous != null) {
            // add if not already contained in the list
            componentList.remove(previous);
        }

        componentList.add(component);
        componentMap.put(component.getId(), component);
        return component;
    }

    @Override
    public void delete(final Long id) {
        final ElectronicComponent component = componentMap.get(id);
        if (component != null) {
            componentList.remove(component);
            componentMap.remove(id);
        }
    }
}
