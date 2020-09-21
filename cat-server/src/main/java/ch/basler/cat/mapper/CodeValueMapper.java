package ch.basler.cat.mapper;

import ch.basler.cat.api.CodeTypeDto;
import ch.basler.cat.api.CodeValueDto;
import ch.basler.cat.model.CodeType;
import ch.basler.cat.model.CodeValue;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class CodeValueMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public CodeValueMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CodeValue mapFromDto(CodeValueDto dto) {
        CodeValue mapped = new CodeValue();
        mapped.setId(dto.getId());
        mapped.setName(dto.getName());
        mapped.setCreator(dto.getCreator());
        mapped.setCodeType(getCodeType(dto.getCodeType()));
        mapped.setValue(dto.getValue());
        return mapped;
    }

    public CodeValueDto mapToDto(CodeValue value) {
        CodeValueDto mapped = new CodeValueDto();
        mapped.setId(value.getId());
        mapped.setName(value.getName());
        mapped.setCreator(value.getCreator());
        mapped.setCodeType(getCodeTypeId(value.getCodeType()));
        mapped.setValue(value.getValue());
        return mapped;
    }

    private CodeType getCodeType(long codeTypeId) {
        CodeTypeDto dto = new CodeTypeDto();
        dto.setId(codeTypeId);
        return modelMapper.map(dto, CodeType.class);
    }

    private long getCodeTypeId(CodeType codeType) {
        CodeTypeDto dto = modelMapper.map(codeType, CodeTypeDto.class);
        return (dto == null) ? 0 : dto.getId();
    }
}
