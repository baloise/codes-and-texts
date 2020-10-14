package ch.basler.cat.api.service.json;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class JsonFileServiceTest {

    private static final Path BASE_DIR = Paths.get("src", "test", "resources");

    private JsonFileService<String> service;

    @Before
    public void setUp() throws Exception {
        service = new JsonFileService<>(BASE_DIR.resolve("stringtexts.json"), String[].class);
    }

    @Test
    public void getDataAsJson() {
        assertEquals("myText", service.getDataAsJson());
    }
}