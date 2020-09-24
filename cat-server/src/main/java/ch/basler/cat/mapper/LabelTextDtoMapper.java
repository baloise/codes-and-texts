package ch.basler.cat.mapper;

import ch.basler.cat.api.LabelTextDto;
import ch.basler.cat.model.LabelText;

public class LabelTextDtoMapper extends DtoMapper<LabelTextDto, LabelText> {

    @Override
    protected LabelTextDto entityToDto(LabelText entity) {
        LabelTextDto mapped = new LabelTextDto();
        mapped.setId(trim(entity.getId()));
        mapped.setAppId(entity.getAppId());
        mapped.setName(trim(entity.getName()));
        mapped.setTextD(trim(entity.getTextD()));
        mapped.setTextF(trim(entity.getTextF()));
        mapped.setTextI(trim(entity.getTextI()));
        mapped.setTextE(trim(entity.getTextE()));
        return mapped;
    }

    @Override
    protected LabelText dtoToEntity(LabelTextDto entity) {
        LabelText mapped = new LabelText();
        mapped.setId(trim(entity.getId()));
        mapped.setAppId(entity.getAppId());
        mapped.setName(trim(entity.getName()));
        mapped.setTextD(trim(entity.getTextD()));
        mapped.setTextF(trim(entity.getTextF()));
        mapped.setTextI(trim(entity.getTextI()));
        mapped.setTextE(trim(entity.getTextE()));
        return mapped;
    }

}
