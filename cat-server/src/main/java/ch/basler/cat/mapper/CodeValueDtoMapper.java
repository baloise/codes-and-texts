package ch.basler.cat.mapper;

import ch.basler.cat.api.CodeValueDto;
import ch.basler.cat.model.CodeValue;

public class CodeValueDtoMapper extends DtoMapper<CodeValueDto, CodeValue> {

    @Override
    protected CodeValueDto entityToDto(CodeValue entity) {
        CodeValueDto mapped = new CodeValueDto();
        mapped.setType(entity.getType());
        mapped.setValue(entity.getValue());
        mapped.setName(trim(entity.getName()));
        return mapped;
    }

    @Override
    protected CodeValue dtoToEntity(CodeValueDto dto) {
        CodeValue mapped = new CodeValue();
        mapped.setType(dto.getType());
        mapped.setValue(dto.getValue());
        mapped.setName(trim(dto.getName()));
        return mapped;
    }

}
