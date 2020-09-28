package ch.basler.cat.controller;

import ch.basler.cat.api.CodeTextDto;
import ch.basler.cat.model.CodeText;
import ch.basler.cat.model.CodeType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CodeTextControllerTest {

    private CodeTextController controller;

    @Before
    public void setUp() throws Exception {
        controller = new CodeTextController(null);
    }

    @Test
    public void convertToEntity() {
        CodeTextDto dto = new CodeTextDto();
        dto.setType(3L);
        dto.setValue(123L);
        dto.setName("testCode");
        dto.setTextD("testText");

        CodeText mapped = controller.convertToEntity(dto);

        assertEquals(dto.getType(), mapped.getType());
        assertEquals(dto.getValue(), mapped.getValue());
        assertEquals(dto.getName(), mapped.getName());
        assertEquals(dto.getTextD(), mapped.getTextD());
    }

    @Test
    public void convertToDto() {
        CodeText entity = new CodeText();
        entity.setType(1L);
        entity.setValue(123L);
        entity.setName("testCode");
        entity.setTextD("testText");

        CodeTextDto mapped = controller.convertToDto(entity);

        assertEquals(entity.getType(), mapped.getType());
        assertEquals(entity.getValue(), mapped.getValue());
        assertEquals(entity.getName(), mapped.getName());
        assertEquals(entity.getTextD(), mapped.getTextD());
    }

}