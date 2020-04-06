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

import ch.basler.cat.api.CodeTypeDto;
import ch.basler.cat.model.CodeType;
import ch.basler.cat.services.CodeTypeRepository;
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class CodeTypeController {

    private static final Logger logger = LoggerFactory.getLogger(CodeTypeController.class);
    private final CodeTypeRepository repository;

    private ModelMapper modelMapper;

    @Autowired
    public CodeTypeController(
            CodeTypeRepository repository,
            ModelMapper modelMapper) {

        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    // Aggregate root
    @GetMapping("/codetypes")
    public List<CodeTypeDto> all() {
        return IterableUtils.toList(this.repository.findAll()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/codetypes")
    public CodeTypeDto create(@RequestBody CodeTypeDto codeTypeDto) {
        CodeType codeType = convertToEntity(codeTypeDto);
        CodeType codeTypeCreated = repository.save(codeType);
        return convertToDto(codeTypeCreated);
    }

    // Single item
    @GetMapping("/codetypes/{id}")
    public CodeTypeDto one(@PathVariable("id") Long id) {
        CodeType codeType = this.repository.findById(id)
                .orElseThrow(() -> new EntityFoundException("codeType", id));

        return convertToDto(codeType);
    }

    @PutMapping("/codetypes/{id}")
    public CodeTypeDto update(@RequestBody CodeTypeDto newCodeTypeDto, @PathVariable Long id) {
        CodeType newCodeType = convertToEntity(newCodeTypeDto);
        return repository.findById(id)
                .map((codeType -> {
                    modelMapper.map(newCodeType, codeType);
                    return convertToDto(repository.save(codeType));
                })).orElseGet(() -> {
                    newCodeType.setId(id);
                    return convertToDto(repository.save(newCodeType));
                });
    }

    @DeleteMapping("/codetypes/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    private CodeTypeDto convertToDto(CodeType codeType) {
        CodeTypeDto codeTypeDto = modelMapper.map(codeType, CodeTypeDto.class);

        return codeTypeDto;
    }

    private CodeType convertToEntity(CodeTypeDto codeTypeDto) {
        CodeType codeType = modelMapper.map(codeTypeDto, CodeType.class);

        return codeType;
    }
}
