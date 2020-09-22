package ch.basler.cat.controller;

import ch.basler.cat.api.ApplicationDto;
import ch.basler.cat.model.Application;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.*;

public class ApplicationControllerTest {

    private ApplicationController controller;

    @Before
    public void setUp() {
        controller = new ApplicationController(null, new ModelMapper());
    }

    @Test
    public void convertToDto() {
        Application entity = new Application();
        entity.setId(1l);
        entity.setCreator("testCreator");
        entity.setName("testName");
        entity.setPackageName("testPackageName");

        ApplicationDto mapped = controller.convertToDto(entity);

        assertEquals(entity.getId().longValue(), mapped.getId());
        assertEquals(entity.getCreator(), mapped.getCreator());
        assertEquals(entity.getName(), mapped.getName());
        assertEquals(entity.getPackageName(), mapped.getPackageName());
    }

    @Test
    public void convertToEntity() {
        ApplicationDto dto = new ApplicationDto();
        dto.setId(1);
        dto.setCreator("testCreator");
        dto.setName("testName");
        dto.setPackageName("testPackageName");

        Application mapped = controller.convertToEntity(dto);

        assertEquals(dto.getId(), mapped.getId().longValue());
        assertEquals(dto.getCreator(), mapped.getCreator());
        assertEquals(dto.getName(), mapped.getName());
        assertEquals(dto.getPackageName(), mapped.getPackageName());
    }
}