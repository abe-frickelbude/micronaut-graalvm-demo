package example.util;

import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.core.io.ResourceResolver;

import javax.inject.Singleton;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A simple startup banner a-la SpringBoot, not available in Micronaut (yet?) for some reason
 *
 * TODO: add configuration option(s) like in SpringBoot (i.e. micronaut.application.banner in application.yml)
 */
@Singleton
public class StartupBanner implements BeanCreatedEventListener<ResourceResolver> {

    /**
     * The ResourceResolver is one of the earliest beans to be constructed, so this bean
     * listens for the corresponding BeanCreatedEvent to kindaaaaaa prioritize itself higher
     * that other beans - prolly a bit unreliable, but what the hell, you know? Better than
     * nuthin'.....
     *
     * Abe's gangsta-style BANNAR!!!!!
     *
     * вєωαяє, ι αм ƒαη¢у!
     */
    @Override
    public ResourceResolver onCreated(BeanCreatedEvent<ResourceResolver> event) {

        var resolver = event.getBean();
        var bannerData = resolver.getResourceAsStream("classpath:banner.txt");
        if(bannerData.isPresent()) {

            try {
                var inputStream = bannerData.get();
                var outStream = new ByteArrayOutputStream();

                inputStream.transferTo(outStream);

                var bannerText = new StringBuilder();
                bannerText.append(outStream);
                bannerText.append("\n");

                System.out.print(bannerText.toString());
            } catch(IOException ex) {
                // can't read banner - swallow this silently as not to pollute the application
                // startup log
            }
        }
        return resolver;
    }
}
