package ch.basler.cat.generator;

import org.junit.Test;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CodesParserTest {

    @Test
    public void exportCodes() throws Exception {
        URI uri = checkUri();
        List<String> strings = Files.readAllLines(Paths.get(uri));
        String json = strings.stream().collect(joining());

        List<CodeType> codeTypes = new CodeParser().parse(json);

        assertEquals(24, codeTypes.size());
    }

    public URI checkUri() throws Exception {
        File file = new File("src/test/resources/test-codes.json");
        //Convert local path to URL
        URL url = file.toURI().toURL();
        //Convert local path to URI
        URI uri = file.toURI();
        return uri;
    }
}
