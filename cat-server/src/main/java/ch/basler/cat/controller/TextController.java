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

import ch.basler.cat.api.TextDto;
import ch.basler.cat.mapper.TextDtoMapper;
import ch.basler.cat.model.Text;
import ch.basler.cat.services.TextRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(description = "Access to texts")
@RestController
public class TextController {

    private static final Logger logger = LoggerFactory.getLogger(TextController.class);
    private final TextRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();
    private final TextDtoMapper dtoMapper = new TextDtoMapper();

    @Autowired
    public TextController(TextRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    @GetMapping("/texts")
    public List<TextDto> all() {
        return IterableUtils.toList(repository.findAll()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/texts")
    public TextDto create(@RequestBody TextDto dto) {
        Text entity = convertToEntity(dto);
        Text entityCreated = repository.save(entity);
        return convertToDto(entityCreated);
    }

    // Single item
    @GetMapping("/texts/{id}")
    public TextDto one(@PathVariable long id) {
        Text codeValue = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("codeValue", id));

        return convertToDto(codeValue);
    }

    @PutMapping("/texts/{id}")
    public TextDto update(@RequestBody TextDto newTextDto,
                          @PathVariable long id) {

        Text newText = convertToEntity(newTextDto);
        return repository.findById(id)
                .map((labelText -> {
                    modelMapper.map(newText, labelText);
                    return convertToDto(repository.save(labelText));
                })).orElseGet(() -> {
                    newText.setId(id);
                    return convertToDto(repository.save(newText));
                });
    }

    @DeleteMapping("/texts/{id}")
    public void delete(@PathVariable long id) {
        repository.deleteById(id);
    }

    TextDto convertToDto(Text codeText) {
        return dtoMapper.mapToDto(codeText);
    }

    Text convertToEntity(TextDto codeTextDto) {
        return dtoMapper.maptoEntity(codeTextDto);
    }
}
