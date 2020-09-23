package ch.basler.cat.mapper;

public abstract class DtoMapper<D,E> {

    public D map(E entity) {
        return (entity == null) ? null : mapToDto(entity);
    }

    protected abstract D mapToDto(E entity);

    protected String trim(String value) {
        return (value == null) ? null : value.trim();
    }
}
