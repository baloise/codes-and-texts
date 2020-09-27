package ch.basler.cat.controller;

import ch.basler.cat.api.DomainDto;
import ch.basler.cat.model.Domain;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DomainControllerTest {

    private DomainController controller;

    @Before
    public void setUp() throws Exception {
        controller = new DomainController(null);
    }

    @Test
    public void convertToDto() {
        Domain entity = new Domain();
        entity.setId(1L);
        entity.setCreator("testCreator");
        entity.setPrefix("testPrefix");
        entity.setPackageName("testPackage");
        entity.setProjectName("testProject");
        entity.setEmail("testEmail");

        DomainDto mapped = controller.convertToDto(entity);

        assertEquals(entity.getId().longValue(), mapped.getId());
        assertEquals(entity.getCreator(), mapped.getCreator());
        assertEquals(entity.getPrefix(), mapped.getPrefix());
        assertEquals(entity.getPackageName(), mapped.getPackageName());
        assertEquals(entity.getProjectName(), mapped.getProjectName());
        assertEquals(entity.getEmail(), mapped.getEmail());
    }

    @Test
    public void convertToEntity() {
        DomainDto dto = new DomainDto();
        dto.setId(1);
        dto.setCreator("testCreator");
        dto.setPrefix("testPrefix");
        dto.setPackageName("testPackage");
        dto.setProjectName("testProject");
        dto.setEmail("testEmail");

        Domain mapped = controller.convertToEntity(dto);

        assertEquals(dto.getId(), mapped.getId().longValue());
        assertEquals(dto.getCreator(), mapped.getCreator());
        assertEquals(dto.getPrefix(), mapped.getPrefix());
        assertEquals(dto.getPackageName(), mapped.getPackageName());
        assertEquals(dto.getProjectName(), mapped.getProjectName());
        assertEquals(dto.getEmail(), mapped.getEmail());
    }
}