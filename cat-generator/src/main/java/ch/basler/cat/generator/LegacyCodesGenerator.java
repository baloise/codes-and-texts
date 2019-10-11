package ch.basler.cat.generator;

import ch.basler.cat.generator.common.FileUtil;
import ch.basler.cat.generator.model.CodeType;
import ch.basler.cat.generator.model.CodeValue;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.joining;

enum CodeTypeTemplateID {
    PACKAGE,
    CLASSNAME,
    CODE_TYPE_ID,
    CODE_TYPE_NAME,
    NUMBER_OF_CODES,
    NUMBER_OF_CODES_PLUS_NULL_CODE
}

enum CodeValueTemplateID {
    CODE_VALUE_NAME,
    CODE_VALUE_ID;
}

public class LegacyCodesGenerator {

    public static final String TEMPLATE_PREFIX = "$";

    public void generate(List<CodeType> codeTypes) throws IOException {
        final List<String> tempateLines = FileUtil.getLines("src/main/resources", "LegacyCodes.template");
        for (CodeType codeType : codeTypes) {
            List<String> contentLines = tempateLines;
            CodeTypeTemplateID[] values = CodeTypeTemplateID.values();
            String classname = toCamelCase(codeType.getName());

            String content = contentLines.stream().map(line -> {
                line = line.replace(TEMPLATE_PREFIX + CodeTypeTemplateID.PACKAGE.name(),
                        codeType.getPackageName() + ".codes");
                line = line.replace(TEMPLATE_PREFIX + CodeTypeTemplateID.CLASSNAME.name(),
                        classname);
                line = line.replace(TEMPLATE_PREFIX + CodeTypeTemplateID.CODE_TYPE_NAME.name(),
                        String.valueOf(codeType.getName()));
                line = line.replace(TEMPLATE_PREFIX + CodeTypeTemplateID.CODE_TYPE_ID.name(),
                        String.valueOf(codeType.getId()));
                line = line.replace(TEMPLATE_PREFIX + CodeTypeTemplateID.NUMBER_OF_CODES.name(),
                        String.valueOf(codeType.getCodeValues().size()));
                line = line.replace(TEMPLATE_PREFIX + CodeTypeTemplateID.NUMBER_OF_CODES_PLUS_NULL_CODE.name(),
                        String.valueOf(codeType.getCodeValues().size() + 1));

                line = replaceAllCodeValues(line, codeType);
                return line;
            }).collect(joining("\n"));

            FileUtil.write("gen", classname + ".java", content);
        }
    }

    private String replaceAllCodeValues(final String line, CodeType codeType) {

        List<CodeValue> codeValues = codeType.getCodeValues();

        boolean relevantForValues = line.contains(TEMPLATE_PREFIX + CodeValueTemplateID.CODE_VALUE_NAME.name())
                || line.contains(TEMPLATE_PREFIX + CodeValueTemplateID.CODE_VALUE_ID.name());
        if (!relevantForValues) {
            return line;
        }
        return codeValues.stream().map(codeValue -> {
            String lineForValue = line;
            lineForValue = lineForValue.replace(TEMPLATE_PREFIX + CodeValueTemplateID.CODE_VALUE_NAME.name(),
                    String.valueOf(codeValue.getName()));
            lineForValue = lineForValue.replace(TEMPLATE_PREFIX + CodeValueTemplateID.CODE_VALUE_ID.name(),
                    String.valueOf(codeValue.getCode()));
            return lineForValue;
        }).collect(joining("\n"));
    }

    private String toCamelCase(String s) {
        String[] parts = s.split("_");
        String camelCaseString = "";
        for (String part : parts) {
            camelCaseString = camelCaseString + toProperCase(part);
        }
        return "Code" + camelCaseString;
    }

    private String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }
}
