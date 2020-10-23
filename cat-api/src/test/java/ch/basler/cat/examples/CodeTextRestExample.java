package ch.basler.cat.examples;

import ch.basler.cat.api.text.CodeText;
import ch.basler.cat.api.code.CodeType;
import ch.basler.cat.api.service.json.JsonRestService;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

public class CodeTextRestExample {

    private final Collection<CodeText> allCodeTexts;

    public static void main(String[] args){
        CodeTextRestExample sampleApp = new CodeTextRestExample(SampleURLs.CODE_TEXT_ROUTE);
        sampleApp.getCodeTexte("BRANCHE", SampleCodes.CODE_TYPE_BRANCHE);
        sampleApp.getCodeTexte("NATION", SampleCodes.CODE_TYPE_NATION);
    }

    public CodeTextRestExample(URI serviceRoute) {
        long start = System.currentTimeMillis();
        allCodeTexts = new JsonRestService<>(serviceRoute, CodeText[].class).getData();
        long duration = System.currentTimeMillis() - start;
        System.out.println(allCodeTexts.size() + " items initialized in " + duration + " ms");
    }

    public Collection<CodeText> getCodeTexte(String name, CodeType codeType) {
        long start = System.currentTimeMillis();
        Collection<CodeText> result = allCodeTexts.stream().filter(cv -> cv.getIdentifier().getType() == codeType.getId()).collect(Collectors.toList());
        long duration = System.currentTimeMillis() - start;
        System.out.println(result.size() + " items found for " + name + " in " + duration + " ms");
        return result;
    }
}
