package ch.basler.cat.controller;

import ch.basler.cat.api.CodeValueDto;
import ch.basler.cat.model.CodeType;
import ch.basler.cat.model.CodeValue;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.*;

public class CodeValueControllerTest {

    private CodeValueController controller;

    @Before
    public void setUp() throws Exception {
        controller = new CodeValueController(null, new ModelMapper());
    }

    @Test
    public void convertToEntity() {
        CodeValueDto dto = new CodeValueDto();
        dto.setId("1");
//        dto.setCodeType(3L);
        dto.setName("testCode");
        dto.setValue(123L);

        CodeValue value = controller.convertToEntity(dto);

        assertEquals("1", value.getId());
//        assertEquals(3L, value.getCodeType().getId());
        assertEquals("testCode", value.getName());
        assertEquals(123L, value.getValue());
    }

    @Test
    public void convertToDto() {
        CodeType testType = new CodeType();
        testType.setId(3L);
        CodeValue value = new CodeValue();
        value.setId("1");
        value.setCodeType(testType);
        value.setName("testCode");
        value.setValue(123L);

        CodeValueDto dto = controller.convertToDto(value);

        assertEquals("1", dto.getId());
//        assertEquals(3L, dto.getCodeType());
        assertEquals("testCode", dto.getName());
        assertEquals(123L, dto.getValue());
    }

}