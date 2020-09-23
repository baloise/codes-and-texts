package ch.basler.cat.mapper;

import ch.basler.cat.api.CodeTextDto;
import ch.basler.cat.model.CodeText;

public class CodeTextDtoMapper extends DtoMapper<CodeTextDto, CodeText> {

    @Override
    protected CodeTextDto mapToDto(CodeText entity) {
        CodeTextDto dto = new CodeTextDto();
        dto.setId(trim(entity.getId()));
        dto.setValue(entity.getValue());
        dto.setTypeId(entity.getTypeId());
        dto.setName(trim(entity.getName()));
        dto.setTextD(trim(entity.getTextD()));
        dto.setTextF(trim(entity.getTextF()));
        dto.setTextI(trim(entity.getTextI()));
        dto.setTextE(trim(entity.getTextE()));
        return dto;
    }

}
