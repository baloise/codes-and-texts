package ch.basler.cat.mapper;

import ch.basler.cat.api.ApplicationDto;
import ch.basler.cat.model.Application;

public class ApplicationDtoMapper extends DtoMapper<ApplicationDto, Application> {

    @Override
    protected ApplicationDto mapToDto(Application entity) {
        ApplicationDto dto = new ApplicationDto();
        dto.setId(entity.getId());
        dto.setName(trim(entity.getName()));
        dto.setPackageName(trim(entity.getPackageName()));
        dto.setCreator(trim(entity.getCreator()));
        return dto;
    }

}
