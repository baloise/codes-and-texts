package ch.basler.cat.mapper;

import ch.basler.cat.api.ResponsibleDto;
import ch.basler.cat.model.Responsible;

public class ResponsibleDtoMapper extends DtoMapper<ResponsibleDto, Responsible> {

    @Override
    protected ResponsibleDto entityToDto(Responsible entity) {
        ResponsibleDto mapped = new ResponsibleDto();
        mapped.setId(entity.getId());
        mapped.setPrefix(trim(entity.getPrefix()));
        mapped.setProjectName(trim(entity.getProjectName()));
        mapped.setPackageName(trim(entity.getPackageName()));
        mapped.setEmail(trim(entity.getEmail()));
        mapped.setCreator(trim(entity.getCreator()));
        return mapped;
    }

    @Override
    protected Responsible dtoToEntity(ResponsibleDto dto) {
        Responsible mapped = new Responsible();
        mapped.setId(dto.getId());
        mapped.setPrefix(trim(dto.getPrefix()));
        mapped.setProjectName(trim(dto.getProjectName()));
        mapped.setPackageName(trim(dto.getPackageName()));
        mapped.setEmail(trim(dto.getEmail()));
        mapped.setCreator(trim(dto.getCreator()));
        return mapped;
    }

}
