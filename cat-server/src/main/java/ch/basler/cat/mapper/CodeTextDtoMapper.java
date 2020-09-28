package ch.basler.cat.mapper;

import ch.basler.cat.api.CodeTextDto;
import ch.basler.cat.model.CodeText;

public class CodeTextDtoMapper extends DtoMapper<CodeTextDto, CodeText> {

    @Override
    protected CodeTextDto entityToDto(CodeText entity) {
        CodeTextDto mapped = new CodeTextDto();
        mapped.setValue(entity.getValue());
        mapped.setType(entity.getType());
        mapped.setName(trim(entity.getName()));
        mapped.setTextD(trim(entity.getTextD()));
        mapped.setTextF(trim(entity.getTextF()));
        mapped.setTextI(trim(entity.getTextI()));
        mapped.setTextE(trim(entity.getTextE()));
        return mapped;
    }

    @Override
    protected CodeText dtoToEntity(CodeTextDto dto) {
        CodeText mapped = new CodeText();
        mapped.setValue(dto.getValue());
        mapped.setType(dto.getType());
        mapped.setName(trim(dto.getName()));
        mapped.setTextD(trim(dto.getTextD()));
        mapped.setTextF(trim(dto.getTextF()));
        mapped.setTextI(trim(dto.getTextI()));
        mapped.setTextE(trim(dto.getTextE()));
        return mapped;
    }

}
