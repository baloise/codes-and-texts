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

import ch.basler.cat.api.DomainDto;
import ch.basler.cat.mapper.DomainDtoMapper;
import ch.basler.cat.model.Domain;
import ch.basler.cat.services.DomainRepository;
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DomainController {

    private static final Logger logger = LoggerFactory.getLogger(DomainController.class);
    private final DomainRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();
    private final DomainDtoMapper dtoMapper = new DomainDtoMapper();

    @Autowired
    public DomainController(DomainRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    @GetMapping("/domains")
    public List<DomainDto> all() {
        return IterableUtils.toList(this.repository.findAll()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/domains")
    public DomainDto create(@RequestBody DomainDto DomainDto) {
        Domain Domain = convertToEntity(DomainDto);
        Domain.setId(null);
        Domain DomainCreated = repository.save(Domain);
        return convertToDto(DomainCreated);
    }

    // Single item
    @GetMapping("/domains/{id}")
    public DomainDto one(@PathVariable("id") Long id) {
        Domain Domain = this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Domain", id));

        return convertToDto(Domain);
    }

    @PutMapping("/domains/{id}")
    public DomainDto update(@RequestBody DomainDto newDomainDto, @PathVariable Long id) {
        Domain newDomain = convertToEntity(newDomainDto);
        return repository.findById(id)
                .map((Domain -> {
                    modelMapper.map(newDomain, Domain);
                    return convertToDto(repository.save(Domain));
                })).orElseGet(() -> {
                    newDomain.setId(id);
                    return convertToDto(repository.save(newDomain));
                });
    }

    @DeleteMapping("/domains/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    DomainDto convertToDto(Domain domain) {
        return dtoMapper.mapToDto(domain);
    }

    Domain convertToEntity(DomainDto domainDto) {
        return dtoMapper.maptoEntity(domainDto);
    }
}
