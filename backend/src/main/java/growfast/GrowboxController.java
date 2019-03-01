package growfast;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping("/growboxes")
public class GrowboxController
{
    private final GrowboxRepository repository;
    private final GrowboxResourceAssembler assembler;

    GrowboxController(GrowboxRepository repository, GrowboxResourceAssembler assembler)
    {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    Resources<Resource<Growbox>> all()
    {
        List<Resource<Growbox>> resources =
                repository
                        .findAll()
                        .stream()
                        .map(assembler::toResource)
                        .collect(Collectors.toList());

        return new Resources<>(resources, linkTo(methodOn(GrowboxController.class).all()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Resource<Growbox> one(@PathVariable String id)
    {
        Growbox box =
                repository
                        .findById(id)
                        .orElseThrow(() -> new GrowboxNotFoundException(id));

        return assembler.toResource(box);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> create(@RequestBody Growbox newBox) throws URISyntaxException
    {
        Resource<Growbox> resource = assembler.toResource(repository.save(newBox));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> delete(@PathVariable String id)
    {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
