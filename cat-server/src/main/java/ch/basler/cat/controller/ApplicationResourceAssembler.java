package ch.basler.cat.controller;

import ch.basler.cat.model.Application;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static ch.basler.cat.controller.ApplicationResourceAssembler.ApplicationResource;

@Component
public class ApplicationResourceAssembler extends RepresentationModelAssemblerSupport<Application, ApplicationResource> {

    public ApplicationResourceAssembler() {
        super(ApplicationController.class, ApplicationResource.class);
    }

    @Override
    public ApplicationResource toModel(Application entity) {
        return new ApplicationResource(entity);
    }

    static class ApplicationResource extends EntityModel<Application> {

        public ApplicationResource(Application content) {
            super(content);
        }
    }
}
