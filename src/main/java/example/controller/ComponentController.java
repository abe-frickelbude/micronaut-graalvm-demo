package example.controller;

import example.model.ElectronicComponent;
import example.service.core.ElectronicComponentRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * "@Controller" flags a controller for route registration in Micronaut's embedded HTTP server
 */
//@Api(
//        tags = {"Component catalog Rest API"}
//)
//@Api(
//        tags = {"Component catalog Rest API"}
//)
@Validated
@Controller("/api/component")
public class ComponentController {

    private final ElectronicComponentRepository componentRepository;

    @Inject
    public ComponentController(ElectronicComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    /**
     * A basic example of a request handler method.
     *
     * <p>
     * - @PathVariable extracts and maps path fragments to parameters
     * - HttpResponse fluent API can be used to provide custom responses, e.g. NOT_FOUND
     * <p>
     */
    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<ElectronicComponent> find(@PathVariable("id") final Long id) {

        final var component = componentRepository.find(id);
        if (component != null) {
            return HttpResponse.ok(component);
        } else {
            return HttpResponse.notFound();
        }
    }

    @Put
    public ElectronicComponent save(@Valid @Body final ElectronicComponent component) {
        return componentRepository.save(component);
    }

    @Delete("/{id}")
    public HttpResponse delete(@PathVariable("id") final Long id) {

        if (componentRepository.find(id) != null) {
            componentRepository.delete(id);
            return HttpResponse.ok();
        } else {
            return HttpResponse.notFound();
        }
    }
}
