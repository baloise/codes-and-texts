package ch.basler.cat.api.examples;

import ch.basler.cat.api.code.CodeType;
import ch.basler.cat.api.code.CodeValue;
import ch.basler.cat.api.service.json.JsonRestService;

import java.util.Collection;

public class CodesConsistencyRestExample {

    private final Collection<CodeType> allCodeTypes;
    private final Collection<CodeValue> allCodeValues;

    public static void main(String[] args){
        CodesConsistencyRestExample sampleApp = new CodesConsistencyRestExample();
        sampleApp.checkCodeType("CODE_TYPE_NATION", SampleCodes.CODE_TYPE_NATION);
        sampleApp.checkCodeType("CODE_TYPE_BRANCHE", SampleCodes.CODE_TYPE_BRANCHE);
        sampleApp.checkCodeValue("NATION_KANADA", SampleCodes.NATION_KANADA);
        sampleApp.checkCodeValue("NATION_SCHWEIZ", SampleCodes.NATION_SCHWEIZ);
        sampleApp.checkCodeValue("BRANCHE_SACH", SampleCodes.BRANCHE_SACH);
        sampleApp.checkCodeValue("BRANCHE_HAFT", SampleCodes.BRANCHE_HAFT);
        sampleApp.checkCodeValue("BRANCHE_LEBEN_PRIVAT", SampleCodes.BRANCHE_LEBEN_PRIVAT);
    }

    public CodesConsistencyRestExample() {
        long start = System.currentTimeMillis();
        allCodeTypes = new JsonRestService<>(SampleURLs.CODE_TYPE_ROUTE, CodeType[].class).getData();
        allCodeValues = new JsonRestService<>(SampleURLs.CODE_VALUE_ROUTE, CodeValue[].class).getData();
        long duration = System.currentTimeMillis() - start;
        System.out.println(allCodeTypes.size() + " types," + allCodeValues.size() + " values initialized in " + duration + " ms");
    }

    public void checkCodeType(String name, CodeType codeType) {
        CodeType codeTypeFromService = allCodeTypes.stream().filter(codeType::equals).findFirst().orElse(null);
        System.out.println(name + ": " + codeType + " -> " + codeTypeFromService);
    }

    public void checkCodeValue(String name, CodeValue codeValue) {
        CodeValue codeValueFromService = allCodeValues.stream().filter(codeValue::equals).findFirst().orElse(null);
        System.out.println(name + ": " + codeValue + " -> " + codeValueFromService);
    }
}
