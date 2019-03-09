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
    private final GrowboxResourceAssembler growboxAssembler;
    private final SensorResourcesAssembler sensorsAssembler;

    GrowboxController(
            GrowboxRepository repository,
            GrowboxResourceAssembler growboxAssembler,
            SensorResourcesAssembler sensorsAssembler)
    {
        this.repository = repository;
        this.growboxAssembler = growboxAssembler;
        this.sensorsAssembler = sensorsAssembler;
    }


    // === Growboxes === //

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    Resources<Resource<Growbox>> all()
    {
        List<Resource<Growbox>> resources =
                repository
                        .findAll()
                        .stream()
                        .map(growboxAssembler::toResource)
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

        return growboxAssembler.toResource(box);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> create(@RequestBody Growbox newBox) throws URISyntaxException
    {
        Resource<Growbox> resource = growboxAssembler.toResource(repository.save(newBox));

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


    // === Sensors === //

    @GetMapping(value = "/{id}/sensors", produces = MediaType.APPLICATION_JSON_VALUE)
    Resources<Resource<Sensor>> sensors(@PathVariable String id)
    {
        Growbox box =
                repository
                        .findById(id)
                        .orElseThrow(() -> new GrowboxNotFoundException(id));

        return sensorsAssembler.toResource(box.getSensors(), box.getId());
    }

    @PostMapping(value = "/{id}/sensors", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> create(@PathVariable String id, @RequestBody Sensor newSensor) throws URISyntaxException
    {
        Growbox box = repository
                .findById(id)
                .orElseThrow(() -> new GrowboxNotFoundException(id));

        box.getSensors().add(newSensor);
        repository.save(box);

        return ResponseEntity
                .created(new URI(growboxAssembler.toResource(box).getLink("sensors").expand().getHref()))
                .body(sensorsAssembler.toResource(box.getSensors(), box.getId()));
    }

    @DeleteMapping(value = "/{id}/sensors/{sensorName}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> delete(@PathVariable String id, @PathVariable String sensorName)
    {
        Growbox box = repository
                .findById(id)
                .orElseThrow(() -> new GrowboxNotFoundException(id));

        box.getSensors().removeIf(sensor -> sensor.getName().equals(sensorName));

        repository.save(box);

        return ResponseEntity.noContent().build();
    }
}
