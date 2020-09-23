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

import ch.basler.cat.api.CodeTextDto;
import ch.basler.cat.mapper.CodeTextDtoMapper;
import ch.basler.cat.model.CodeText;
import ch.basler.cat.services.CodeTextRepository;
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CodeTextController {

    private static final Logger logger = LoggerFactory.getLogger(CodeTextController.class);
    private final CodeTextRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();
    private final CodeTextDtoMapper dtoMapper = new CodeTextDtoMapper();

    @Autowired
    public CodeTextController(CodeTextRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    @GetMapping("/codetexte")
    public List<CodeTextDto> all() {
        return IterableUtils.toList(repository.findAll()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/codetypes/{typeId}/codetexte")
    public List<CodeTextDto> all(@PathVariable long typeId) {
        return IterableUtils.toList(repository.findByTypeId(typeId)).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/codetexte")
    public CodeTextDto create(@RequestBody CodeTextDto codeTextDto) {
        CodeText codeText = convertToEntity(codeTextDto);
        CodeText codeTextCreated = repository.save(codeText);
        return convertToDto(codeTextCreated);
    }

    // Single item
    @GetMapping("/codetexte/{id}")
    public CodeTextDto one(@PathVariable("id") String id) {
        CodeText codeValue = repository.findById(id)
                .orElseThrow(() -> new EntityFoundException("codeValue", id));

        return convertToDto(codeValue);
    }

    @PutMapping("/codetexte/{id}")
    public CodeTextDto update(@RequestBody CodeTextDto newCodeTextDto,
                               @PathVariable("id") String id) {

        CodeText newCodeText = convertToEntity(newCodeTextDto);
        return repository.findById(id)
                .map((codeText -> {
                    modelMapper.map(newCodeText, codeText);
                    return convertToDto(repository.save(codeText));
                })).orElseGet(() -> {
                    newCodeText.setId(id);
                    return convertToDto(repository.save(newCodeText));
                });
    }

    @DeleteMapping("/codetexte/{id}")
    public void delete(@PathVariable("id") String id) {
        repository.deleteById(id);
    }

    CodeTextDto convertToDto(CodeText codeText) {
        return dtoMapper.map(codeText);
    }

    CodeText convertToEntity(CodeTextDto codeTextDto) {
        return modelMapper.map(codeTextDto, CodeText.class);
    }
}
