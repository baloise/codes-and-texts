package ch.basler.cat.controller;

import ch.basler.cat.api.CodeValueDto;
import ch.basler.cat.model.CodeValue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CodeValueControllerTest {

    private CodeValueController controller;

    @Before
    public void setUp() throws Exception {
        controller = new CodeValueController(null);
    }

    @Test
    public void convertToEntity() {
        CodeValueDto dto = new CodeValueDto();
        dto.setId("1");
        dto.setTypeId(3L);
        dto.setName("testCode");
        dto.setValue(123L);

        CodeValue value = controller.convertToEntity(dto);

        assertEquals("1", value.getId());
        assertEquals(3L, value.getTypeId());
        assertEquals("testCode", value.getName());
        assertEquals(123L, value.getValue());
    }

    @Test
    public void convertToDto() {
        CodeValue value = new CodeValue();
        value.setId("1");
        value.setTypeId(3L);
        value.setName("testCode");
        value.setValue(123L);

        CodeValueDto dto = controller.convertToDto(value);

        assertEquals("1", dto.getId());
        assertEquals(3L, dto.getTypeId());
        assertEquals("testCode", dto.getName());
        assertEquals(123L, dto.getValue());
    }

}