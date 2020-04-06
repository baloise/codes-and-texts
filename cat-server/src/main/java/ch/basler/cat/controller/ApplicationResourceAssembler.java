/*
 * Copyright 2020 Baloise Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.basler.cat.controller;

import ch.basler.cat.model.Application;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static ch.basler.cat.controller.ApplicationResourceAssembler.ApplicationResource;

@Component
public class ApplicationResourceAssembler
        extends RepresentationModelAssemblerSupport<Application, ApplicationResource> {

    public ApplicationResourceAssembler() {
        super(ApplicationController.class, ApplicationResource.class);
    }

    @Override
    public ApplicationResource toModel(Application entity) {
        return new ApplicationResource(entity);
    }

    static class ApplicationResource extends EntityModel<Application> {

        ApplicationResource(Application content) {
            super(content);
        }
    }
}
