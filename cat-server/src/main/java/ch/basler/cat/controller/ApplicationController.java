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

import ch.basler.cat.api.ApplicationDto;
import ch.basler.cat.mapper.ApplicationDtoMapper;
import ch.basler.cat.model.Application;
import ch.basler.cat.services.ApplicationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(description = "Access to applications using texts")
@RestController
public class ApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    private final ApplicationRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();
    private final ApplicationDtoMapper dtoMapper = new ApplicationDtoMapper();

    @Autowired
    public ApplicationController(ApplicationRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    @ApiOperation(value = "Get all applications", notes = "Returns a list of all applications")
    @GetMapping("/applications")
    @PreAuthorize("isAuthenticated()")
    public List<ApplicationDto> all() {
        return IterableUtils.toList(this.repository.findAll()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Add a new application")
    @PostMapping("/applications")
    public ApplicationDto create(@RequestBody ApplicationDto applicationDto) {

        Application application = convertToEntity(applicationDto);
        application.setId(null);
        Application applicationCreated = repository.save(application);
        return convertToDto(applicationCreated);
    }

    @ApiOperation(value = "Find an application by ID", notes = "Returns a single application")
    @GetMapping("/applications/{id}")
    public ApplicationDto one(@PathVariable("id") Long id) {
        Application application = this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("application", id));

        return convertToDto(application);
    }

    @ApiOperation(value = "Update an application")
    @PutMapping("/applications/{id}")
    public ApplicationDto update(@RequestBody ApplicationDto newApplicationDto, @PathVariable Long id) {
        Application newApplication = convertToEntity(newApplicationDto);
        return repository.findById(id)
                .map((application -> {
                    modelMapper.map(newApplication, application);
                    return convertToDto(repository.save(application));
                })).orElseGet(() -> {
                    newApplication.setId(id);
                    return convertToDto(repository.save(newApplication));
                });
    }

    @ApiOperation(value = "Delete an application")
    @DeleteMapping("/applications/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    ApplicationDto convertToDto(Application application) {
        return dtoMapper.mapToDto(application);
    }

    Application convertToEntity(ApplicationDto applicationDto) {
        return dtoMapper.maptoEntity(applicationDto);
    }
}

