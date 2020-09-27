package ch.basler.cat.mapper;

import ch.basler.cat.api.DomainDto;
import ch.basler.cat.model.Domain;

public class DomainDtoMapper extends DtoMapper<DomainDto, Domain> {

    @Override
    protected DomainDto entityToDto(Domain entity) {
        DomainDto mapped = new DomainDto();
        mapped.setId(entity.getId());
        mapped.setPrefix(trim(entity.getPrefix()));
        mapped.setProjectName(trim(entity.getProjectName()));
        mapped.setPackageName(trim(entity.getPackageName()));
        mapped.setEmail(trim(entity.getEmail()));
        mapped.setCreator(trim(entity.getCreator()));
        return mapped;
    }

    @Override
    protected Domain dtoToEntity(DomainDto dto) {
        Domain mapped = new Domain();
        mapped.setId(dto.getId());
        mapped.setPrefix(trim(dto.getPrefix()));
        mapped.setProjectName(trim(dto.getProjectName()));
        mapped.setPackageName(trim(dto.getPackageName()));
        mapped.setEmail(trim(dto.getEmail()));
        mapped.setCreator(trim(dto.getCreator()));
        return mapped;
    }

}
