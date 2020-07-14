package example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.model.ElectronicComponent;
import example.service.core.ElectronicComponentRepository;
import io.micronaut.core.beans.BeanMap;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.views.View;

import javax.inject.Inject;
import java.util.Map;

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
    public HttpResponse<Map<String,Object>> getDetailPage(@PathVariable("id") final Long id) {

        final ElectronicComponent component = componentRepository.find(id);
        if(component != null) {
            // again, i'm a bit lazy here -> just dumped component details into a map for easier access in the
            // Thymeleaf template
            //Map<String, Object> data = objectMapper.convertValue(component, Map.class);
            //model.addAttribute("componentData", data);
            var componentData = BeanMap.of(component);
            Map<String, Object> data = Map.of("componentData", componentData);
            return HttpResponse.ok(data);

        } else {
            return HttpResponse.notFound();
            //return "404";
        }
    }
//
//    @Get("/list")
//    public String getComponentList(final Model model,
//                                   @RequestParam(value = "page", defaultValue = "1") Integer currentPage,
//                                   @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
//
//        final Page<ElectronicComponent> componentPage
//                = componentRepository.find(PageRequest.of(currentPage -1, pageSize));
//
//        model.addAttribute("componentPage", componentPage);
//
//        // add page numbers for links
//        int totalPages = componentPage.getTotalPages();
//        if(totalPages > 0) {
//
//            // baeh... being lazy resulted in a syntactic mess here :-(
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//
//        // this is the name of the associated Thymeleaf template
//        return "component_list";
//    }


}
