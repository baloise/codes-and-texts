package ch.basler.cat.api.service.json;

import java.io.IOException;
import java.nio.file.*;

public class JsonFileService<T> extends JsonService<T> {

    private final Path jsonFile;

    public JsonFileService(Path jsonFile, Class<T[]> clazz) {
        super(clazz);
        this.jsonFile = jsonFile;
    }

    @Override
    protected String getDataAsJson() {
        try {
            return new String(Files.readAllBytes(jsonFile));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
