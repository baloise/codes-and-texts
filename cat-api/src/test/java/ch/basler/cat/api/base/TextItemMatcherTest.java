package ch.basler.cat.api.base;

import ch.basler.cat.api.text.MessageText;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TextItemMatcherTest {

    public static final Number TEXT_ID = 12345;

    private TextItemMatcher<Number> matcher;

    @Before
    public void setUp() throws Exception {
        matcher = new TextItemMatcher<>(TEXT_ID);
    }

    @Test
    public void matches() {
        assertFalse("null value", matcher.matches(null));
        assertFalse("empty value", matcher.matches(mock(MessageText.class)));
        assertFalse("null id", matcher.matches(createTextItem(null)));
        assertFalse("wrong id", matcher.matches(createTextItem(123)));
        assertTrue("correct id", matcher.matches(createTextItem(12345)));
    }

    private TextItem<Number> createTextItem(Number number) {
        TextItem<Number> textItem = mock(TextItem.class);
        when(textItem.getIdentifier()).thenReturn(number);
        return textItem;
    }
}