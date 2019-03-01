package growfast;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class GrowboxResourceAssembler implements ResourceAssembler<Growbox, Resource<Growbox>>
{
    @Override
    public Resource<Growbox> toResource(Growbox box)
    {
        Resource<Growbox> resource = new Resource<>(box,
                linkTo(methodOn(GrowboxController.class).one(box.getId())).withSelfRel(),
                linkTo(methodOn(GrowboxController.class).all()).withRel("growboxes"));

        return resource;
    }
}
