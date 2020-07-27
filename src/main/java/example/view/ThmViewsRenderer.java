package example.view;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.beans.BeanMap;
import io.micronaut.core.io.ResourceLoader;
import io.micronaut.core.io.Writable;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import io.micronaut.core.util.ArgumentUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.ViewUtils;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.views.thymeleaf.ThymeleafViewsRenderer;
import io.micronaut.views.thymeleaf.WebContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.exceptions.TemplateEngineException;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This is an effective, if somewhat inelegant fix for the Micronaut's bundled
 * ThymeleafViewsRenderer.
 * <p>
 * The code is in this class is mostly copied from the original ThymeleafViewsRenderer,
 * with some minor improvements. This class can also be considered an example of
 * "@Replaces" annotation usage for replacing an existing bean with a different one.
 * <p>
 * The original does not observe the semantics of the
 * core HTTP router w.r.t. to exception handling, or this may
 * be a general deficiency in the HTTP router implementation.
 * <p>
 * Specifically, throwing Micronaut's own ViewRenderingException has no effect in the current version as
 * it is not caught by the response rendering code, and will therefore propagate
 * up the stack and eventually lock up the main server event loop (assuming the default
 * reactive/netty implementation is used).
 * <p>
 * With that being said, the HTTP router does catch IOException cases and cleanly
 * responds to them with an HTTP 500 and a human-readable JSON response. Therefore
 * this fix simply adds a try/catch that wraps Thymeleaf's TemplateEngineException and
 * throws from the writeTo() of the returned Writable instances when a template error
 * is encountered, thus respecting the Writable's semantics.
 * <p>
 * I've opened a relevant bug for micronaut-views project here:
 * https://github.com/micronaut-projects/micronaut-views/issues/91
 */
@Produces(MediaType.TEXT_HTML)
@Singleton
@Replaces(ThymeleafViewsRenderer.class)
public class ThmViewsRenderer implements ViewsRenderer {

    private final ResourceLoader resourceLoader;
    private final TemplateEngine templateEngine;
    private final AbstractConfigurableTemplateResolver templateResolver;

    @Inject
    public ThmViewsRenderer(AbstractConfigurableTemplateResolver templateResolver,
                            TemplateEngine templateEngine,
                            ClassPathResourceLoader resourceLoader) {
        this.templateResolver = templateResolver;
        this.templateEngine = templateEngine;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public boolean exists(@NonNull String viewName) {

        String location = templateResolver.getPrefix() +
                ViewUtils.normalizeFile(viewName, templateResolver.getSuffix()) +
                templateResolver.getSuffix();

        return resourceLoader.getResourceAsStream(location).isPresent();
    }

    @NonNull
    @Override
    public Writable render(@NonNull String viewName, @Nullable Object data) {
        ArgumentUtils.requireNonNull("viewName", viewName);
        return (writer) -> {
            IContext context = new Context(Locale.US, variables(data));
            render(viewName, context, writer);
        };
    }

    @Override
    @NonNull
    public Writable render(@NonNull String viewName, @Nullable Object data,
                           @NonNull HttpRequest<?> request) {
        ArgumentUtils.requireNonNull("viewName", viewName);
        ArgumentUtils.requireNonNull("request", request);
        return (writer) -> {
            IContext context = new WebContext(request, Locale.US, variables(data));
            render(viewName, context, writer);
        };
    }

    /**
     * Passes the arguments as is to {@link TemplateEngine#process(String, IContext, Writer)}.
     *
     * @param viewName The view name
     * @param context  The context
     * @param writer   The writer
     */
    private void render(final String viewName, final IContext context, final Writer writer)
            throws IOException {
        try {
            templateEngine.process(viewName, context, writer);
        } catch (TemplateEngineException ex) {

            final var msg = "Error rendering Thymeleaf view [" + viewName + "]";
            throw new IOException(msg, ex);
        }
    }

    private static Map<String, Object> variables(@Nullable Object data) {
        if (data == null) {
            return new HashMap<>();
        }
        if (data instanceof Map) {
            return (Map<String, Object>) data;
        } else {
            return BeanMap.of(data);
        }
    }
}
