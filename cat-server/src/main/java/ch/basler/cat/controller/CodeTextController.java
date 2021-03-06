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
import ch.basler.cat.model.CodeTextId;
import ch.basler.cat.model.CodeTextRaw;
import ch.basler.cat.services.CodeTextRepository;
import ch.basler.cat.services.CodeTextRepositoryRaw;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(description = "Access to text representation of codes")
@RestController
public class CodeTextController {

    private static final Logger logger = LoggerFactory.getLogger(CodeTextController.class);
    private final CodeTextRepository repository;
    private final CodeTextRepositoryRaw repositoryRaw;

    private final ModelMapper modelMapper = new ModelMapper();
    private final CodeTextDtoMapper dtoMapper = new CodeTextDtoMapper();

    @Autowired
    public CodeTextController(CodeTextRepository repository, CodeTextRepositoryRaw repositoryRaw) {
        this.repository = repository;
        this.repositoryRaw = repositoryRaw;
    }

    // Aggregate root
    @GetMapping("/codetexts")
    public List<CodeTextDto> all() {
        return IterableUtils.toList(repository.findAll()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/domains/{domain}/codetexts")
    public List<CodeTextDto> byDomain(@PathVariable long domain) {
        return IterableUtils.toList(repository.findByDomain(domain)).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/codetypes/{type}/codetexts")
    public List<CodeTextDto> byType(@PathVariable long type) {
        return IterableUtils.toList(repository.findByType(type)).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/codetexts")
    public CodeTextDto create(@RequestBody CodeTextDto codeTextDto) {
        CodeText codeText = convertToEntity(codeTextDto);
        Optional<CodeText> codeTextOpt = saveCodeText(codeText);
        return codeTextOpt.isPresent() ? convertToDto(codeTextOpt.get()) : null;
    }

    // Single item
    @GetMapping("/codetypes/{type}/codetexts/{value}")
    public CodeTextDto one(@PathVariable long type, @PathVariable long value) {
        CodeText codeValue = repository.findById(CodeTextId.of(type, value))
                .orElseThrow(() -> new EntityNotFoundException("codeValue", type + ":" + value));

        return convertToDto(codeValue);
    }

    @PutMapping("/codetypes/{type}/codetexts/{value}")
    public CodeTextDto update(@RequestBody CodeTextDto newCodeTextDto,
                                     @PathVariable long type, @PathVariable long value) {

        CodeText newCodeText = convertToEntity(newCodeTextDto);

        CodeTextId id = CodeTextId.of(type, value);
        Optional<CodeText> codeTextOpt = repositoryRaw.findById(id)
                .map(codeText -> {
                    modelMapper.map(newCodeText, codeText);
                    return saveCodeText(newCodeText);
                }).orElseGet(() -> saveCodeText(newCodeText));
        return codeTextOpt.isPresent() ? convertToDto(codeTextOpt.get()) : null;
    }

    private Optional<CodeText> saveCodeText(CodeText newCodeText) {
        CodeTextRaw codeTextRaw = new CodeTextRaw(newCodeText);
        CodeTextRaw savedCodeTextRaw = repositoryRaw.save(codeTextRaw);
        return repository.findById(CodeTextId.of(savedCodeTextRaw.getType(), savedCodeTextRaw.getValue()));
    }

    @DeleteMapping("/codetypes/{type}/codetexts/{value}")
    public void delete(@PathVariable long type, @PathVariable long value) {
        repositoryRaw.deleteById(CodeTextId.of(type, value));
    }

    CodeTextDto convertToDto(CodeText codeText) {
        return dtoMapper.mapToDto(codeText);
    }

    CodeText convertToEntity(CodeTextDto codeTextDto) {
        return dtoMapper.maptoEntity(codeTextDto);
    }
}
