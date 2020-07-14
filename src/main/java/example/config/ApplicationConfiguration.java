package example.config;

import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.i18n.ResourceBundleMessageSource;

/**
 * The @Factory construct functions similarly to Spring's @Configuration-annotated
 * classes (is a singleton itself, and registers singleton beans in the application
 * context)
 * <p>
 * In this case, we define a message source for using i18n messages in view
 * templates. The Thymeleaf view module will automatically pick up this and
 * any other defined message sources when resolving messages during rendering.
 */
@Factory
class ApplicationConfiguration {

    @Bean
    MessageSource messageSource() {
        return new ResourceBundleMessageSource("messages");
    }
}
