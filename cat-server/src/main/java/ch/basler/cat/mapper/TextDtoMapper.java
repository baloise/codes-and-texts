package ch.basler.cat.mapper;

import ch.basler.cat.api.TextDto;
import ch.basler.cat.model.Text;

public class TextDtoMapper extends DtoMapper<TextDto, Text> {

    @Override
    protected TextDto entityToDto(Text entity) {
        TextDto mapped = new TextDto();
        mapped.setId(entity.getId());
        mapped.setType(entity.getType());
        mapped.setTextD(trim(entity.getTextD()));
        mapped.setTextF(trim(entity.getTextF()));
        mapped.setTextI(trim(entity.getTextI()));
        mapped.setTextE(trim(entity.getTextE()));
        return mapped;
    }

    @Override
    protected Text dtoToEntity(TextDto entity) {
        Text mapped = new Text();
        mapped.setId(entity.getId());
        mapped.setType(entity.getType());
        mapped.setTextD(trim(entity.getTextD()));
        mapped.setTextF(trim(entity.getTextF()));
        mapped.setTextI(trim(entity.getTextI()));
        mapped.setTextE(trim(entity.getTextE()));
        return mapped;
    }

}
