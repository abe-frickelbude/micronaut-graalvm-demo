package example.service.component_generator;

import example.model.ElectronicComponent;
import example.util.RandomValueUtils;
import io.micronaut.context.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A factory class that generates various types of {@linkplain ElectronicComponent} with randomly
 * assigned values.
 *
 * @author Ibragim Kuliev
 */
@Singleton
public class RandomComponentFactory {

    private static Logger log = LoggerFactory.getLogger(RandomComponentFactory.class);

    private final ApplicationContext appContext;

    private Map<Class<?>, ComponentBuilder<?>> builderRegistry;
    private List<Class<?>> componentClasses;

    @Inject
    public RandomComponentFactory(final ApplicationContext appContext) {
        this.appContext = appContext;
    }

    @PostConstruct
    public void init() {
        builderRegistry = new HashMap<>();
        componentClasses = new ArrayList<>();
        for (var builder : appContext.getBeansOfType(ComponentBuilder.class)) {

            componentClasses.add(builder.getComponentClass());
            builderRegistry.put(builder.getComponentClass(), builder);
            log.info("Registered component builder for {}",
                    builder.getComponentClass().getSimpleName());
        }
    }

    public List<Class<?>> getComponentClasses() {
        return new ArrayList<>(componentClasses);
    }

    public ElectronicComponent makeComponent() {
        var componentClass = RandomValueUtils.pickRandomValue(componentClasses);
        var builder = builderRegistry.get(componentClass);
        var component = builder.buildComponent();
        return component;
    }
}
