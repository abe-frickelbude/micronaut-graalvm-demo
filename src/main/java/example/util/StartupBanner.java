package example.util;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StartupBanner implements
        BeanCreatedEventListener<ApplicationEventListener> {

    private static final Logger log = LoggerFactory.getLogger(StartupBanner.class);

    @Inject
    private ApplicationContext appContext;

    @PostConstruct
    private void init() {

//        appContext.getAllBeanDefinitions()
//                .stream()
//                .filter(def -> def.getName().contains("io.micronaut"))
//                .forEach(def -> {
//                            log.info("Bean: {} -> {}", def.getName(), def.getBeanType());
//                        });

        // ????
        // ResourceResolver <- works well, early init
        // ClassPathResourceLoader
        // DefaultApplicationContext <- X
        // ApplicationEventListener <- too late, multiple events!
        // LogbackLoggingSystem <- too late!
        // ApplicationConfiguration <- too late!
    }

    @Override
    public ApplicationEventListener onCreated(BeanCreatedEvent<ApplicationEventListener> event) {
        log.info("App context created!");
        return event.getBean();
    }

    // this one NEVER gets called for some reason -> Bug in Micronaut ?!
//    @Override
//    public ResourceLoader onInitialized(BeanInitializingEvent<ResourceLoader> event) {
//
//        log.info("App context initialized!");
//        return event.getBean();
//    }
}
