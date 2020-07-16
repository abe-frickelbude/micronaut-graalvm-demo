package example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.model.ElectronicComponent;
import example.service.core.ElectronicComponentRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.*;
import io.micronaut.views.View;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@Api(
//        tags = {"Component catalog"}
//)
@Controller("/catalog")
public class ComponentCatalogController {

    private final ElectronicComponentRepository componentRepository;
    private final ObjectMapper objectMapper;

    @Inject
    public ComponentCatalogController(final ElectronicComponentRepository componentRepository,
                                      final ObjectMapper objectMapper) {
        this.componentRepository = componentRepository;
        this.objectMapper = objectMapper;
    }

    /**
     *
     */
    @Get("/detail/{id}")
    @View("component_detail_page")
    public HttpResponse<Map<String, Object>> getDetailPage(@PathVariable("id") final Long id) {

        final ElectronicComponent component = componentRepository.find(id);
        if (component != null) {

            /* A small rationale here: using Jackson's ObjectMapper to "render"
             * a POJO to map respects the @JsonPropertyOrder annotation parameters
             * on the said pojo and results a "stable" order in which the keys
             * are inserted into the target map.
             *
             * On the other hand using e.g. Micronaut's BeanMap.of(), while likely more performant,
             * doesn't have a defined order in which map entries are generated, and
             * the resulting HTML view contents will always be "randomly shuffled"
             */
            var componentData = objectMapper.convertValue(component, Map.class);
            Map<String, Object> data = Map.of("componentData", componentData);
            return HttpResponse.ok(data);
        } else {
            return HttpResponse.notFound();
            //return "404";
        }
    }

    /**
     * Example of a local request error handler that uses a specific
     * view template to "redirect" an HTTP_NOT_FOUND in the getDetailPage() above
     */
    @Error(status = HttpStatus.NOT_FOUND)
    @View("error/component_not_found")
    public HttpResponse onComponentNotFound() {
        return HttpResponse.notFound();
    }

    /**
     * Another example of a HTTP route handler, this time with optional query
     * parameters, injectable via @QueryValue annotations
     */
    @View("component_list")
    @Get("/list{?page,size}")
    public HttpResponse<Map<String, Object>> getComponentList(
            @QueryValue(value = "page", defaultValue = "1") Integer currentPage,
            @QueryValue(value = "size", defaultValue = "20") Integer pageSize) {

        final Page<ElectronicComponent> componentPage
                = componentRepository.find(Pageable.from(currentPage - 1, pageSize));

        var model = new HashMap<String, Object>();
        model.put("componentPage", componentPage);

        // add page numbers for links
        int totalPages = componentPage.getTotalPages();
        if (totalPages > 0) {

            // baeh... being lazy resulted in a syntactic mess here :-(
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed().collect(Collectors.toList());
            model.put("pageNumbers", pageNumbers);
        }
        return HttpResponse.ok(model);
    }
}
