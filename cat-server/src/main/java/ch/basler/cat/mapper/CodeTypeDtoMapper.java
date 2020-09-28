package ch.basler.cat.mapper;

import ch.basler.cat.api.CodeTypeDto;
import ch.basler.cat.model.CodeType;

public class CodeTypeDtoMapper extends DtoMapper<CodeTypeDto, CodeType> {

    @Override
    protected CodeTypeDto entityToDto(CodeType entity) {
        CodeTypeDto mapped = new CodeTypeDto();
        mapped.setId(entity.getId());
        mapped.setName(trim(entity.getName()));
        mapped.setDomain(entity.getDomainId());
        mapped.setCreator(trim(entity.getCreator()));
        return mapped;
    }

    @Override
    protected CodeType dtoToEntity(CodeTypeDto dto) {
        CodeType mapped = new CodeType();
        mapped.setId(dto.getId());
        mapped.setName(trim(dto.getName()));
        mapped.setDomainId(dto.getDomain());
        mapped.setCreator(trim(dto.getCreator()));
        return mapped;
    }

}
