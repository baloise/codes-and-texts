package ch.basler.cat.mapper;

import ch.basler.cat.api.LabelTextDto;
import ch.basler.cat.model.LabelText;

public class LabelTextDtoMapper extends DtoMapper<LabelTextDto, LabelText> {

    @Override
    protected LabelTextDto mapToDto(LabelText entity) {
        LabelTextDto dto = new LabelTextDto();
        dto.setId(trim(entity.getId()));
        dto.setAppId(entity.getAppId());
        dto.setName(trim(entity.getName()));
        dto.setTextD(trim(entity.getTextD()));
        dto.setTextF(trim(entity.getTextF()));
        dto.setTextI(trim(entity.getTextI()));
        dto.setTextE(trim(entity.getTextE()));
        return dto;
    }

}
