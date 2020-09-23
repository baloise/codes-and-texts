package ch.basler.cat.mapper;

import ch.basler.cat.api.CodeTypeDto;
import ch.basler.cat.model.CodeType;

public class CodeTypeDtoMapper extends DtoMapper<CodeTypeDto, CodeType> {

    @Override
    protected CodeTypeDto mapToDto(CodeType entity) {
        CodeTypeDto dto = new CodeTypeDto();
        dto.setId(entity.getId());
        dto.setName(trim(entity.getName()));
        dto.setResponsible(entity.getResponsibleId());
        dto.setCreator(trim(entity.getCreator()));
        return dto;
    }

}
