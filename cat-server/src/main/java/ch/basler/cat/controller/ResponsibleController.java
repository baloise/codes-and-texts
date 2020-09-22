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

import ch.basler.cat.api.ResponsibleDto;
import ch.basler.cat.model.Responsible;
import ch.basler.cat.services.ResponsibleRepository;
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ResponsibleController {

    private static final Logger logger = LoggerFactory.getLogger(ResponsibleController.class);
    private final ResponsibleRepository repository;

    private ModelMapper modelMapper;

    @Autowired
    public ResponsibleController(
            ResponsibleRepository repository,
            ModelMapper modelMapper) {

        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    // Aggregate root
    @GetMapping("/responsibles")
    public List<ResponsibleDto> all() {
        return IterableUtils.toList(this.repository.findAll()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/responsibles")
    public ResponsibleDto create(@RequestBody ResponsibleDto responsibleDto) {
        Responsible responsible = convertToEntity(responsibleDto);
        responsible.setId(null);
        Responsible responsibleCreated = repository.save(responsible);
        return convertToDto(responsibleCreated);
    }

    // Single item
    @GetMapping("/responsibles/{id}")
    public ResponsibleDto one(@PathVariable("id") Long id) {
        Responsible responsible = this.repository.findById(id)
                .orElseThrow(() -> new EntityFoundException("responsible", id));

        return convertToDto(responsible);
    }

    @PutMapping("/responsibles/{id}")
    public ResponsibleDto update(@RequestBody ResponsibleDto newResponsibleDto, @PathVariable Long id) {
        Responsible newResponsible = convertToEntity(newResponsibleDto);
        return repository.findById(id)
                .map((responsible -> {
                    modelMapper.map(newResponsible, responsible);
                    return convertToDto(repository.save(responsible));
                })).orElseGet(() -> {
                    newResponsible.setId(id);
                    return convertToDto(repository.save(newResponsible));
                });
    }

    @DeleteMapping("/responsibles/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    ResponsibleDto convertToDto(Responsible responsible) {
        return modelMapper.map(responsible, ResponsibleDto.class);
    }

    Responsible convertToEntity(ResponsibleDto responsibleDto) {
        return modelMapper.map(responsibleDto, Responsible.class);
    }
}
