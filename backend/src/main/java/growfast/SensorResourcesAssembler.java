package growfast;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
class SensorResourcesAssembler
{
    public Resources<Resource<Sensor>> toResource(List<Sensor> sensors, String growboxId)
    {
        List<Resource<Sensor>> list =
                sensors
                        .stream()
                        .map(sensor -> new Resource<>(sensor))
                        .collect(Collectors.toList());

        return new Resources<Resource<Sensor>>(list,
                linkTo(methodOn(GrowboxController.class).sensors(growboxId)).withSelfRel(),
                linkTo(methodOn(GrowboxController.class).one(growboxId)).withRel("growbox"));
    }
}
