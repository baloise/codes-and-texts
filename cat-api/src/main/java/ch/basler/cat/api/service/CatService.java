package ch.basler.cat.api.service;

import java.util.Collection;

public interface CatService<T> {

    Collection<T> getData();

}
