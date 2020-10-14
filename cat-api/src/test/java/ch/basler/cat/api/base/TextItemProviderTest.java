package ch.basler.cat.api.base;

import ch.basler.cat.api.service.json.JsonService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TextItemProviderTest {

    private static final String TEST_NAME = "testName";
    private static final String OTHER_NAME = "otherName";

    private TextItemProvider<TextItem<String>, String> provider;
    private JsonService<TextItem<String>> testService;

    @Before
    public void setUp() throws Exception {
        testService = mock(JsonService.class);
        TextItem<String> testItem = createItem(TEST_NAME);
        TextItem<String> otherItem = createItem(OTHER_NAME);
        when(testService.getData()).thenReturn(Arrays.asList(testItem, otherItem));

        provider = new TextItemProvider<>(testService);
    }

    @Test
    public void getItemWithCaching() {
        Optional<TextItem<String>> testItem1 = provider.getItem(TEST_NAME);
        Optional<TextItem<String>> testItem2 = provider.getItem(TEST_NAME);
        Optional<TextItem<String>> otherItem = provider.getItem(OTHER_NAME);
        Optional<TextItem<String>> invalidItem = provider.getItem("invalidName");

        assertTrue("testItem1", testItem1.isPresent());
        assertEquals(testItem1, testItem2);
        assertTrue("otherItem", otherItem.isPresent());
        assertFalse("invalidItem", invalidItem.isPresent());

        // service only called once
        verify(testService, times(1)).getData();

        provider.clear();

        Optional<TextItem<String>> testItem3 = provider.getItem(TEST_NAME);
        assertEquals(testItem1, testItem3);

        // service called after clear
        verify(testService, times(2)).getData();
    }

    private TextItem<String> createItem(String name) {
        TextItem<String> textItem = mock(TextItem.class);
        when(textItem.getIdentifier()).thenReturn(name);
        return textItem;
    }


}