package ch.basler.cat.api.service.json;

import ch.basler.cat.api.service.CatService;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collection;

public abstract class JsonService<T> implements CatService<T> {

    private final Gson gson = new Gson();

    private final Class<T[]> clazz;

    public JsonService(Class<T[]> clazz) {
        this.clazz = clazz;
    }

    protected abstract String getDataAsJson();

    public Collection<T> getData() {
        String dataAsJson = getDataAsJson();
        T[] fromJson = gson.fromJson(dataAsJson, clazz);
        return Arrays.asList(fromJson);
    }

}
