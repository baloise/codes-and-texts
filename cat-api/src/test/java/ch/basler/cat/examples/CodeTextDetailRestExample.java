package ch.basler.cat.examples;

import ch.basler.cat.api.code.CodeValue;
import ch.basler.cat.api.service.json.JsonRestService;
import ch.basler.cat.api.service.json.JsonService;
import ch.basler.cat.api.text.CodeText;
import ch.basler.cat.api.text.CodeTextId;
import ch.basler.cat.api.base.Language;
import ch.basler.cat.api.base.TextItemProvider;

public class CodeTextDetailRestExample {

    static JsonRestService<CodeText> TEXT_SERVICE_REST = new JsonRestService<>(SampleURLs.CODE_TEXT_ROUTE, CodeText[].class);

    private final TextItemProvider<CodeText, CodeTextId> codeTextProvider;

    public CodeTextDetailRestExample(TextItemProvider<CodeText, CodeTextId> codeTextProvider) {
        this.codeTextProvider = codeTextProvider;
    }

    public static void main(String[] args) {
        JsonService<CodeText> codeTextService = TEXT_SERVICE_REST;

        CodeTextDetailRestExample sampleApp = new CodeTextDetailRestExample(new TextItemProvider<>(codeTextService));
        sampleApp.displayTextsFor("NATION_KANADA", SampleCodes.NATION_KANADA);
        sampleApp.displayTextsFor("NATION_SCHWEIZ", SampleCodes.NATION_SCHWEIZ);
        sampleApp.displayTextsFor("BRANCHE_SACH", SampleCodes.BRANCHE_SACH);
        sampleApp.displayTextsFor("BRANCHE_HAFT", SampleCodes.BRANCHE_HAFT);
        sampleApp.displayTextsFor("BRANCHE_LEBEN_PRIVAT", SampleCodes.BRANCHE_LEBEN_PRIVAT);
    }

    private void displayTextsFor(String codeName, CodeValue codeValue) {
        CodeText codeText = codeTextProvider.getItem(CodeTextId.fromCodeValue(codeValue)).orElse(null);
        System.out.println("Texts for " + codeName + ": ");
        System.out.println("DE: " + codeText.getText(Language.DE));
        System.out.println("FR: " + codeText.getText(Language.FR));
        System.out.println("IT: " + codeText.getText(Language.IT));
        System.out.println("EN: " + codeText.getText(Language.EN));
    }
}
