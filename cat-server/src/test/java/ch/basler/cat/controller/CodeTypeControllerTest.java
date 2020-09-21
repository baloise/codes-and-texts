package ch.basler.cat.controller;

import ch.basler.cat.api.CodeTypeDto;
import ch.basler.cat.model.CodeType;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.*;

public class CodeTypeControllerTest {


    private CodeTypeController controller;

    @Before
    public void setUp() throws Exception {
        controller = new CodeTypeController(null, new ModelMapper());
    }

    @Test
    public void convertToDto() {
        CodeType type = new CodeType();
        type.setId(1);
        type.setName("testName");
        type.setCreator("testCreator");

        CodeTypeDto mapped = controller.convertToDto(type);

        assertEquals(type.getId(), mapped.getId());
        assertEquals(type.getName(), mapped.getName());
        assertEquals(type.getCreator(), mapped.getCreator());
    }

    @Test
    public void convertToEntity() {
        CodeTypeDto dto = new CodeTypeDto();
        dto.setId(1);
        dto.setName("testName");
        dto.setCreator("testCreator");
        dto.setResponsible(3L);

        CodeType mapped = controller.convertToEntity(dto);

        assertEquals(dto.getId(), mapped.getId());
        assertEquals(dto.getName(), mapped.getName());
        assertEquals(dto.getCreator(), mapped.getCreator());
    }

}