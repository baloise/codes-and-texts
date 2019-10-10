package ch.basler.cat.generator;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class CodeParser {

    public static final String CONTENT_ID = "content";
    public static final String CODE_TYPE_NAME_ID = "name";
    public static final String CODE_VALUES_ID = "codeValues";

    public List<CodeType> parse(String json) {

        JsonParser parser = new JacksonJsonParser();
        Map<String, Object> stringObjectMap = parser.parseMap(json);
        List<Map> codeTypeList = (List<Map>) stringObjectMap.get(CONTENT_ID);
        return codeTypeList.stream().map(ct -> parseCodeType(ct)).collect(toList());
    }

    private CodeType parseCodeType(Map ct) {
        CodeType codeType = new CodeType((String) ct.get(CODE_TYPE_NAME_ID));
        List<Map> codeValueMap = (List<Map>) ct.get(CODE_VALUES_ID);
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
