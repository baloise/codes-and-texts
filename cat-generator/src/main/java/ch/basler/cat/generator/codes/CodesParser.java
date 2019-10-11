package ch.basler.cat.generator.codes;

import ch.basler.cat.generator.codes.model.CodeType;
import ch.basler.cat.generator.codes.model.CodeValue;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class CodesParser {

    public static final String CONTENT = "content";
    public static final String CODE_TYPE_NAME = "name";
    public static final String CODE_TYPE_PACKAGE = "packageName";
    public static final String CODE_TYPE_ID = "id";
    public static final String CODE_VALUES = "codeValues";

    public List<CodeType> parse(String json) {

        JsonParser parser = new JacksonJsonParser();
        Map<String, Object> stringObjectMap = parser.parseMap(json);
        List<Map> codeTypeList = (List<Map>) stringObjectMap.get(CONTENT);
        return codeTypeList.stream().map(ct -> parseCodeType(ct)).collect(toList());
    }

    private CodeType parseCodeType(Map ct) {
        CodeType codeType = new CodeType(
                (String) ct.get(CODE_TYPE_NAME), (String)ct.get(CODE_TYPE_PACKAGE), (Integer) ct.get(CODE_TYPE_ID));

        List<Map> codeValueMap = (List<Map>) ct.get(CODE_VALUES);
        addValues(codeType, codeValueMap);
        return codeType;
    }

    private void addValues(CodeType codeType, List<Map> codeValueMap) {
        codeValueMap.stream()
                .forEach(map -> {
                    codeType.addCodeValue(new CodeValue((String) map.get("name"), (Integer) map.get("value")));
                });
    }

}
