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

import ch.basler.cat.api.LabelTextDto;
import ch.basler.cat.mapper.LabelTextDtoMapper;
import ch.basler.cat.model.LabelText;
import ch.basler.cat.services.LabelTextRepository;
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LabelTextController {

    private static final Logger logger = LoggerFactory.getLogger(LabelTextController.class);
    private final LabelTextRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();
    private final LabelTextDtoMapper dtoMapper = new LabelTextDtoMapper();

    @Autowired
    public LabelTextController(LabelTextRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    @GetMapping("/labeltexte")
    public List<LabelTextDto> all() {
        return IterableUtils.toList(repository.findAll()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/applications/{appId}/labeltexte")
    public List<LabelTextDto> all(@PathVariable long appId) {
        return IterableUtils.toList(repository.findByAppId(appId)).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/labeltexte")
    public LabelTextDto create(@RequestBody LabelTextDto dto) {
        LabelText entity = convertToEntity(dto);
        LabelText entityCreated = repository.save(entity);
        return convertToDto(entityCreated);
    }

    // Single item
    @GetMapping("/labeltexte/{id}")
    public LabelTextDto one(@PathVariable("id") String id) {
        LabelText codeValue = repository.findById(id)
                .orElseThrow(() -> new EntityFoundException("codeValue", id));

        return convertToDto(codeValue);
    }

    @PutMapping("/labeltexte/{id}")
    public LabelTextDto update(@RequestBody LabelTextDto newLabelTextDto,
                               @PathVariable("id") String id) {

        LabelText newLabelText = convertToEntity(newLabelTextDto);
        return repository.findById(id)
                .map((labelText -> {
                    modelMapper.map(newLabelText, labelText);
                    return convertToDto(repository.save(labelText));
                })).orElseGet(() -> {
                    newLabelText.setId(id);
                    return convertToDto(repository.save(newLabelText));
                });
    }

    @DeleteMapping("/labeltexte/{id}")
    public void delete(@PathVariable("id") String id) {
        repository.deleteById(id);
    }

    LabelTextDto convertToDto(LabelText codeText) {
        return dtoMapper.mapToDto(codeText);
    }

    LabelText convertToEntity(LabelTextDto codeTextDto) {
        return dtoMapper.maptoEntity(codeTextDto);
    }
}
