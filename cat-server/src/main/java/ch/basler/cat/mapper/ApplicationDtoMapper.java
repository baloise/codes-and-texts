package ch.basler.cat.mapper;

import ch.basler.cat.api.ApplicationDto;
import ch.basler.cat.model.Application;

public class ApplicationDtoMapper extends DtoMapper<ApplicationDto, Application> {

    @Override
    protected ApplicationDto entityToDto(Application entity) {
        ApplicationDto mapped = new ApplicationDto();
        mapped.setId(entity.getId());
        mapped.setName(trim(entity.getName()));
        mapped.setPackageName(trim(entity.getPackageName()));
        mapped.setCreator(trim(entity.getCreator()));
        return mapped;
    }

    @Override
    protected Application dtoToEntity(ApplicationDto dto) {
        Application mapped = new Application();
        mapped.setId(dto.getId());
        mapped.setName(trim(dto.getName()));
        mapped.setPackageName(trim(dto.getPackageName()));
        mapped.setCreator(trim(dto.getCreator()));
        return mapped;
    }
}
