package ch.basler.cat.api;

import ch.basler.cat.api.base.Language;
import ch.basler.cat.api.code.CodeType;
import ch.basler.cat.api.code.CodeValue;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class DefaultCatServiceTextsTest {

    private static final Path BASE_DIR = Paths.get("src", "test", "resources");

    private DefaultCatServiceTexts texts;

    @Before
    public void setUp() throws Exception {
        texts = new DefaultCatServiceTexts(BASE_DIR);
    }

    @Test
    public void getTextForCode() {
        CodeValue codeValue = new CodeValue(new CodeType((short) 1), (short) 1);
        assertEquals("Austria", texts.getTextForCode(codeValue, Language.EN));
    }

    @Test
    public void getTextForLabel() {
        assertEquals("erstes Label", texts.getTextForLabel("LBL_FirstLabel", Language.DE));
    }

    @Test
    public void getTextForMessage() {
        assertEquals("erste Meldung", texts.getTextForMessage(123, Language.DE));
    }

}