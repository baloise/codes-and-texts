package ch.basler.cat.controller;

import ch.basler.cat.api.ResponsibleDto;
import ch.basler.cat.model.Responsible;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.*;

public class ResponsibleControllerTest {

    private ResponsibleController controller;

    @Before
    public void setUp() throws Exception {
        controller = new ResponsibleController(null, new ModelMapper());
    }

    @Test
    public void convertToDto() {
        Responsible entity = new Responsible();
        entity.setId(1L);
        entity.setCreator("testCreator");
        entity.setPrefix("testPrefix");
        entity.setPackageName("testPackage");
        entity.setProjectName("testProject");
        entity.setEmail("testEmail");

        ResponsibleDto mapped = controller.convertToDto(entity);

        assertEquals(entity.getId().longValue(), mapped.getId());
        assertEquals(entity.getCreator(), mapped.getCreator());
        assertEquals(entity.getPrefix(), mapped.getPrefix());
        assertEquals(entity.getPackageName(), mapped.getPackageName());
        assertEquals(entity.getProjectName(), mapped.getProjectName());
        assertEquals(entity.getEmail(), mapped.getEmail());
    }

    @Test
    public void convertToEntity() {
        ResponsibleDto dto = new ResponsibleDto();
        dto.setId(1);
        dto.setCreator("testCreator");
        dto.setPrefix("testPrefix");
        dto.setPackageName("testPackage");
        dto.setProjectName("testProject");
        dto.setEmail("testEmail");

        Responsible mapped = controller.convertToEntity(dto);

        assertEquals(dto.getId(), mapped.getId().longValue());
        assertEquals(dto.getCreator(), mapped.getCreator());
        assertEquals(dto.getPrefix(), mapped.getPrefix());
        assertEquals(dto.getPackageName(), mapped.getPackageName());
        assertEquals(dto.getProjectName(), mapped.getProjectName());
        assertEquals(dto.getEmail(), mapped.getEmail());
    }
}