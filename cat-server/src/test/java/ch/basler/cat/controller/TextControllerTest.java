package ch.basler.cat.controller;

import ch.basler.cat.api.TextDto;
import ch.basler.cat.model.Text;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextControllerTest {

    private TextController controller;

    @Before
    public void setUp() throws Exception {
        controller = new TextController(null);
    }

    @Test
    public void convertToEntity() {
        TextDto dto = new TextDto();
        dto.setId(1);
        dto.setType(3);
        dto.setTextD("testText");

        Text mapped = controller.convertToEntity(dto);

        assertEquals(1, mapped.getId());
        assertEquals(3, mapped.getType());
        assertEquals("testText", mapped.getTextD());
    }

    @Test
    public void convertToDto() {
        Text value = new Text();
        value.setId(1);
        value.setType(5);
        value.setTextD("testText");

        TextDto dto = controller.convertToDto(value);

        assertEquals(1, dto.getId());
        assertEquals(5, dto.getType());
        assertEquals("testText", dto.getTextD());
    }

}