package ch.basler.cat.controller;

import ch.basler.cat.api.CodeTypeDto;
import ch.basler.cat.model.CodeType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CodeTypeControllerTest {

    private CodeTypeController controller;

    @Before
    public void setUp() throws Exception {
        controller = new CodeTypeController(null);
    }

    @Test
    public void convertToDto() {
        CodeType type = new CodeType();
        type.setId(1L);
        type.setDomainId(5);
        type.setName("testName");
        type.setCreator("testCreator");

        CodeTypeDto mapped = controller.convertToDto(type);

        assertEquals(type.getId().longValue(), mapped.getId());
        assertEquals(type.getDomainId(), mapped.getDomain());
        assertEquals(type.getName(), mapped.getName());
        assertEquals(type.getCreator(), mapped.getCreator());
    }

    @Test
    public void convertToEntity() {
        CodeTypeDto dto = new CodeTypeDto();
        dto.setId(1);
        dto.setDomain(5);
        dto.setName("testName");
        dto.setCreator("testCreator");
        dto.setDomain(3L);

        CodeType mapped = controller.convertToEntity(dto);

        assertEquals(dto.getId(), mapped.getId().longValue());
        assertEquals(dto.getDomain(), mapped.getDomainId());
        assertEquals(dto.getName(), mapped.getName());
        assertEquals(dto.getCreator(), mapped.getCreator());
    }

}