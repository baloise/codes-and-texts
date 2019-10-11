package ch.basler.cat.generator.common;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class FileUtil {

    public static String  read(String path, String fileName) throws IOException {
        List<String> strings = getLines(path, fileName);
        return strings.stream().collect(joining());
    }

    public static List<String> getLines(String path, String fileName) throws IOException {
        File file = new File(path, fileName);
        URI uri = file.toURI();
        return Files.readAllLines(Paths.get(uri));
    }

    public static void write(String pathAsString, String fileName, String content) throws IOException {
        new File(pathAsString).mkdirs();
        File file = new File(pathAsString, fileName);
        URI uri = file.toURI();
        Path path = Paths.get(uri);
        Files.write(path, content.getBytes());
    }
}
