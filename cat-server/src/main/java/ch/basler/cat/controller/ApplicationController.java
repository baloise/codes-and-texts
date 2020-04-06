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
import ch.basler.cat.services.ApplicationRepository;
import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    private final ApplicationRepository repository;

    @Autowired
    public ApplicationController(
            ApplicationRepository repository) {

        this.repository = repository;
    }

    // Aggregate root
    @GetMapping("/applications")
    public List<Application> all() {
        return IterableUtils.toList(this.repository.findAll());
    }

    @PostMapping("/applications")
    public Application create(@RequestBody Application application) {
        return repository.save(application);
    }

    // Single item
    @GetMapping("/applications/{id}")
    public Application one(@PathVariable("id") Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new EntityFoundException("application", id));
    }

    @PutMapping("/applications/{id}")
    public Application update(@RequestBody Application newApplication, @PathVariable Long id) {
        return repository.findById(id)
                .map((application -> {
                    application.setName(newApplication.getName());
                    return repository.save(application);
                })).orElseGet(() -> {
                    newApplication.setId(id);
                    return repository.save(newApplication);
                });
    }

    @DeleteMapping("/applications/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
