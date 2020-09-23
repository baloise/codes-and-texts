package ch.basler.cat.mapper;

import ch.basler.cat.api.ResponsibleDto;
import ch.basler.cat.model.Responsible;

public class ResponsibleDtoMapper extends DtoMapper<ResponsibleDto, Responsible> {

    @Override
    protected ResponsibleDto mapToDto(Responsible entity) {
        ResponsibleDto dto = new ResponsibleDto();
        dto.setId(entity.getId());
        dto.setPrefix(trim(entity.getPrefix()));
        dto.setProjectName(trim(entity.getProjectName()));
        dto.setPackageName(trim(entity.getPackageName()));
        dto.setEmail(trim(entity.getEmail()));
        dto.setCreator(trim(entity.getCreator()));
        return dto;
    }

}
