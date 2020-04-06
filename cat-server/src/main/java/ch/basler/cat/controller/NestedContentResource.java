package ch.basler.cat.controller;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

public class NestedContentResource<T> extends RepresentationModel {
    private final CollectionModel<T> nested;

    public NestedContentResource(Iterable<T> toNest) {
        this.nested = new CollectionModel<>(toNest);
    }

    @JsonUnwrapped
    public CollectionModel<T> getNested() {
        return this.nested;
    }
}
