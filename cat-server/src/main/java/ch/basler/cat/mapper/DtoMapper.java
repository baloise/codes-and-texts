package ch.basler.cat.mapper;

public abstract class DtoMapper<D,E> {

    public D mapToDto(E entity) {
        return (entity == null) ? null : entityToDto(entity);
    }

    public E maptoEntity(D dto) {
        return (dto == null) ? null : dtoToEntity(dto);
    }

    protected abstract D entityToDto(E entity);

    protected abstract E dtoToEntity(D dto);

    protected String trim(String value) {
        return (value == null) ? null : value.trim();
    }
}
