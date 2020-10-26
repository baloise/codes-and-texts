package ch.basler.cat.examples.textapi;

import ch.basler.cat.api.DefaultCatServiceTexts;
import ch.basler.cat.api.TextAPI;
import ch.basler.cat.api.base.Language;
import ch.basler.cat.api.code.CodeType;
import ch.basler.cat.api.code.CodeValue;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class TextAPIFileExample {

    private static final Path BASE_DIR = Paths.get("src", "test", "resources");

    private final TextAPI textAPI = new DefaultCatServiceTexts(BASE_DIR);

    @Test
    public void getTextForCode() {
        CodeValue codeAustria = new CodeValue(new CodeType((short) 1), (short) 1);
        assertEquals("Austria", textAPI.getTextForCode(codeAustria, Language.EN));
    }

    @Test
    public void getTextForLabel() {
        assertEquals("erstes Label", textAPI.getTextForLabel("LBL_FirstLabel", Language.DE));
        assertEquals(null, textAPI.getTextForLabel("LBL_SecondLabel", Language.IT));
        assertEquals("third Label", textAPI.getTextForLabel("LBL_ThirdLabel", Language.EN));
    }

    @Test
    public void getTextForMessage() {
        assertEquals("erste Meldung", textAPI.getTextForMessage(123, Language.DE));
        assertEquals(null, textAPI.getTextForMessage(456, Language.FR));
        assertEquals("third Message", textAPI.getTextForMessage(789, Language.EN));
    }

}
