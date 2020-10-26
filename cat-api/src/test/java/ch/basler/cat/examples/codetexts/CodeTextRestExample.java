package ch.basler.cat.examples.codetexts;

import ch.basler.cat.api.code.CodeValue;
import ch.basler.cat.api.service.CatService;
import ch.basler.cat.api.service.json.JsonRestService;
import ch.basler.cat.api.text.CodeText;
import ch.basler.cat.api.text.CodeTextId;
import ch.basler.cat.api.base.Language;
import ch.basler.cat.api.base.TextItemProvider;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertNotNull;

public class CodeTextRestExample {

    private static final URI CODE_TEXT_URI = URI.create("http://localhost:8088/domains/3/codetexts");

    private final CatService<CodeText> service = new JsonRestService<>(CODE_TEXT_URI, CodeText[].class);
    private final TextItemProvider<CodeText, CodeTextId> codeTextProvider = new TextItemProvider<>(service);

    @Test
    public void displayNationTexts() {
        displayTextsFor("NATION_KANADA", SampleCodes.NATION_KANADA);
        displayTextsFor("NATION_SCHWEIZ", SampleCodes.NATION_SCHWEIZ);
    }

    @Test
    public void displayBrancheTexts() {
        displayTextsFor("BRANCHE_SACH", SampleCodes.BRANCHE_SACH);
        displayTextsFor("BRANCHE_HAFT", SampleCodes.BRANCHE_HAFT);
        displayTextsFor("BRANCHE_LEBEN_PRIVAT", SampleCodes.BRANCHE_LEBEN_PRIVAT);
    }

    private void displayTextsFor(String codeName, CodeValue codeValue) {
        CodeText codeText = codeTextProvider.getItem(CodeTextId.fromCodeValue(codeValue)).orElse(null);

        assertNotNull(codeText);

        System.out.println("Texts for " + codeName + ": ");
        System.out.println("DE: " + codeText.getText(Language.DE));
        System.out.println("FR: " + codeText.getText(Language.FR));
        System.out.println("IT: " + codeText.getText(Language.IT));
        System.out.println("EN: " + codeText.getText(Language.EN));
    }
}
