package ch.basler.cat.mapper;

import ch.basler.cat.api.CodeValueDto;
import ch.basler.cat.model.CodeValue;

public class CodeValueDtoMapper extends DtoMapper<CodeValueDto, CodeValue> {

    @Override
    protected CodeValueDto mapToDto(CodeValue entity) {
        CodeValueDto dto = new CodeValueDto();
        dto.setId(trim(entity.getId()));
        dto.setValue(entity.getValue());
        dto.setName(trim(entity.getName()));
        return dto;
    }

}
