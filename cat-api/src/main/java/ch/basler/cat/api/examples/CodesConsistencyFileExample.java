package ch.basler.cat.api.examples;

import ch.basler.cat.api.code.CodeType;
import ch.basler.cat.api.code.CodeValue;
import ch.basler.cat.api.service.json.JsonFileService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class CodesConsistencyFileExample {

    private static final Path BASE_DIR = Paths.get("src", "test", "resources");

    public static final CodeType CODE_TYPE_KANTON = new CodeType((short) 2);

    public static final CodeValue KANTON_GL = new CodeValue(CODE_TYPE_KANTON, (short) 8);
    public static final CodeValue KANTON_BS = new CodeValue(CODE_TYPE_KANTON, (short) 12);

    private final Collection<CodeValue> kantonCodeValues;

    public static void main(String[] args){
        CodesConsistencyFileExample sampleApp = new CodesConsistencyFileExample();
        sampleApp.checkCodeValue("KANTON_GL", KANTON_GL);
        sampleApp.checkCodeValue("KANTON_BS", KANTON_BS);
    }

    public CodesConsistencyFileExample() {
        System.out.println("Using basedir " + BASE_DIR.toAbsolutePath());
        kantonCodeValues = new JsonFileService<>(BASE_DIR.resolve("codekanton.json"), CodeValue[].class).getData();
    }

    public void checkCodeValue(String name, CodeValue codeValue) {
        CodeValue codeValueFromService = kantonCodeValues.stream().filter(codeValue::equals).findFirst().orElse(null);
        System.out.println(name + ": " + codeValue + " -> " + codeValueFromService);
    }
}
