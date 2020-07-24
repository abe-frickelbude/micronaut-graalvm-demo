package example.hacks;

import io.micronaut.core.io.Writable;
import io.micronaut.http.HttpRequest;
import io.micronaut.views.exceptions.ViewRenderingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.io.IOException;

@Aspect
public class ThymeleafViewsRendererDecorator {

    private static final String RETURN_TYPE = "io.micronaut.core.io.Writable";
    private static final String HTTP_REQUEST_TYPE = "io.micronaut.http.HttpRequest";

    private static final String RENDER_1 =
            RETURN_TYPE + " io.micronaut.views.thymeleaf.ThymeleafViewsRenderer.render(String, Object)";

    private static final String RENDER_2 =
            RETURN_TYPE  + " io.micronaut.views.thymeleaf.ThymeleafViewsRenderer.render(String, Object," + HTTP_REQUEST_TYPE + ")";

    // Writable render(String viewName, Object data)
    // Writable render(String viewName, Object data, HttpRequest<?> request)

    // sig is "<return type> fully-qualified method name(argument types without names)"

    @Around(value = "execution(" + RENDER_1 + ") && args(viewName, data)",
            argNames = "joinPoint,viewName,data")
    public Writable render(final ProceedingJoinPoint joinPoint,
                           String viewName, Object data) throws Throwable {

        try {
            return (Writable) joinPoint.proceed();

        } catch (ViewRenderingException ex) {
            throw new IOException(ex);
        }
    }

    @Around(value = "execution(" + RENDER_2 + ") && args(viewName, data, request)",
            argNames = "joinPoint,viewName,data,request")
    public Writable render(final ProceedingJoinPoint joinPoint, String viewName,
                           Object data, HttpRequest<?> request) throws Throwable {
        try {
            return (Writable) joinPoint.proceed();

        } catch (ViewRenderingException ex) {
            throw new IOException(ex);
        }
    }
}
