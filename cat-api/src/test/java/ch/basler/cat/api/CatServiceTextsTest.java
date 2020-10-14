package ch.basler.cat.api;

import ch.basler.cat.api.base.Language;
import ch.basler.cat.api.base.TextItem;
import ch.basler.cat.api.code.CodeType;
import ch.basler.cat.api.code.CodeValue;
import ch.basler.cat.api.service.CatService;
import ch.basler.cat.api.text.CodeText;
import ch.basler.cat.api.text.LabelText;
import ch.basler.cat.api.text.MessageText;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CatServiceTextsTest {

    private CatService<CodeText> codeTextService;
    private CatService<LabelText> labelTextService;
    private CatService<MessageText> messageTextService;

    private CatServiceTexts texts;

    @Before
    public void setUp() throws Exception {
        codeTextService = mock(CatService.class);
        labelTextService = mock(CatService.class);
        messageTextService = mock(CatService.class);
        texts = new CatServiceTexts(codeTextService, labelTextService, messageTextService);
    }

    @Test
    public void getTextForCode() {
        CodeValue myCodeValue = new CodeValue(new CodeType((short) 123), (short)456);
        CodeValue otherCodeValue = new CodeValue(new CodeType((short) 111), (short)333);
        CodeValue invalidCodeValue = new CodeValue(new CodeType((short) 123), (short)333);

        CodeText myCodeText = fillMockItem(new CodeText(myCodeValue), "myCodeText");
        CodeText otherCodeText = fillMockItem(new CodeText(otherCodeValue), "otherCodeText");

        when(codeTextService.getData()).thenReturn(Arrays.asList(myCodeText, otherCodeText));

        assertEquals("myCodeText_DE", texts.getTextForCode(myCodeValue, Language.DE));
        assertEquals("otherCodeText_FR", texts.getTextForCode(otherCodeValue, Language.FR));

        assertNull("no language", texts.getTextForCode(myCodeValue, null));
        assertNull("invalid code", texts.getTextForCode(invalidCodeValue, Language.DE));

        verify(codeTextService, times(1)).getData();
        verifyNoMoreInteractions(codeTextService, labelTextService, messageTextService);
    }

    @Test
    public void getTextForLabel() {
        LabelText myCodeText = fillMockItem(new LabelText("myLabel"), "myLabelText");
        LabelText otherCodeText = fillMockItem(new LabelText("otherLabel"), "otherLabelText");

        when(labelTextService.getData()).thenReturn(Arrays.asList(myCodeText, otherCodeText));

        assertEquals("myLabelText_DE", texts.getTextForLabel("myLabel", Language.DE));
        assertEquals("otherLabelText_IT", texts.getTextForLabel("otherLabel", Language.IT));

        assertNull("no language", texts.getTextForLabel("myLabel", null));
        assertNull("invalid code", texts.getTextForLabel("invalidLabel", Language.DE));

        verify(labelTextService, times(1)).getData();
        verifyNoMoreInteractions(codeTextService, labelTextService, messageTextService);
    }

    @Test
    public void getTextForMessage() {
        MessageText myCodeText = fillMockItem(new MessageText(123), "myMessageText");
        MessageText otherCodeText = fillMockItem(new MessageText(456), "otherMessageText");

        when(messageTextService.getData()).thenReturn(Arrays.asList(myCodeText, otherCodeText));

        assertEquals("myMessageText_DE", texts.getTextForMessage(123, Language.DE));
        assertEquals("otherMessageText_EN", texts.getTextForMessage(456, Language.EN));

        assertNull("no language", texts.getTextForMessage(123, null));
        assertNull("invalid code", texts.getTextForMessage(789, Language.DE));

        verify(messageTextService, times(1)).getData();
        verifyNoMoreInteractions(codeTextService, labelTextService, messageTextService);
    }

    private <T extends TextItem<?>> T fillMockItem(T item, String text) {
        T spy = spy(item);
        when(spy.getTextD()).thenReturn(text + "_DE");
        when(spy.getTextF()).thenReturn(text + "_FR");
        when(spy.getTextI()).thenReturn(text + "_IT");
        when(spy.getTextE()).thenReturn(text + "_EN");
        return spy;
    }
}