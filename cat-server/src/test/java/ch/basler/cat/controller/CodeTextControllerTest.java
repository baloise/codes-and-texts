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
        dto.setId("1");
//        dto.setCodeType(3L);
        dto.setName("testCode");
        dto.setValue(123L);

        CodeText value = controller.convertToEntity(dto);

        assertEquals("1", value.getId());
        assertEquals("testCode", value.getName());
        assertEquals(123L, value.getValue());
    }

    @Test
    public void convertToDto() {
        CodeType testType = new CodeType();
        testType.setId(3L);
        CodeText value = new CodeText();
        value.setId("1");
        value.setName("testCode");
        value.setValue(123L);

        CodeTextDto dto = controller.convertToDto(value);

        assertEquals("1", dto.getId());
        assertEquals("testCode", dto.getName());
        assertEquals(123L, dto.getValue());
    }

}