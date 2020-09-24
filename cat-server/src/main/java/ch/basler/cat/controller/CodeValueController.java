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

import ch.basler.cat.api.CodeValueDto;
import ch.basler.cat.mapper.CodeValueDtoMapper;
import ch.basler.cat.model.CodeValue;
import ch.basler.cat.model.CodeValueId;
import ch.basler.cat.services.CodeValueRepository;
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CodeValueController {

    private static final Logger logger = LoggerFactory.getLogger(CodeValueController.class);
    private final CodeValueRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();
    private final CodeValueDtoMapper dtoMapper = new CodeValueDtoMapper();

    @Autowired
    public CodeValueController(CodeValueRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    @GetMapping("/codevalues")
    public List<CodeValueDto> all() {
        return IterableUtils.toList(repository.findAll()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/codetypes/{typeId}/codevalues")
    public List<CodeValueDto> all(@PathVariable long typeId) {
        return IterableUtils.toList(repository.findByTypeId(typeId)).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/codevalues")
    public CodeValueDto create(@RequestBody CodeValueDto codeValueDto) {
        CodeValue codeValue = convertToEntity(codeValueDto);
        codeValue.setId(null);
        if (codeValue.getCreator() == null) {
            codeValue.setCreator("");
        }
        CodeValue codeValueCreated = repository.save(codeValue);
        return convertToDto(codeValueCreated);
    }

    // Single item
    @GetMapping("/codevalues/{id}")
    public CodeValueDto one(@PathVariable("id") String id) {
        CodeValue codeValue = repository.findById(CodeValueId.of(id))
                .orElseThrow(() -> new EntityFoundException("codeValue", id));

        return convertToDto(codeValue);
    }

    @PutMapping("/codevalues/{id}")
    public CodeValueDto update(@RequestBody CodeValueDto newCodeValueDto,
                               @PathVariable("id") String id) {

        CodeValue newCodeValue = convertToEntity(newCodeValueDto);
        return repository.findById(CodeValueId.of(id))
                .map((codeValue -> {
                    modelMapper.map(newCodeValue, codeValue);
                    return convertToDto(repository.save(codeValue));
                })).orElseGet(() -> {
                    newCodeValue.setId(id);
                    return convertToDto(repository.save(newCodeValue));
                });
    }

    @DeleteMapping("/codevalues/{id}")
    public void delete(@PathVariable("id") String id) {
        repository.deleteById(CodeValueId.of(id));
    }

    CodeValueDto convertToDto(CodeValue codeValue) {
        return dtoMapper.mapToDto(codeValue);
    }

    CodeValue convertToEntity(CodeValueDto codeValueDto) {
        return dtoMapper.maptoEntity(codeValueDto);
    }
}
