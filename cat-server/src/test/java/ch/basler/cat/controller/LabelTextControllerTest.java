package ch.basler.cat.controller;

import ch.basler.cat.api.CodeTextDto;
import ch.basler.cat.api.LabelTextDto;
import ch.basler.cat.model.CodeText;
import ch.basler.cat.model.CodeType;
import ch.basler.cat.model.LabelText;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LabelTextControllerTest {

    private LabelTextController controller;

    @Before
    public void setUp() throws Exception {
        controller = new LabelTextController(null);
    }

    @Test
    public void convertToEntity() {
        LabelTextDto dto = new LabelTextDto();
        dto.setId("1");
        dto.setAppId(3);
        dto.setName("testCode");

        LabelText value = controller.convertToEntity(dto);

        assertEquals("1", value.getId());
        assertEquals(3, value.getAppId());
        assertEquals("testCode", value.getName());
    }

    @Test
    public void convertToDto() {
        LabelText value = new LabelText();
        value.setId("1");
        value.setAppId(5);
        value.setName("testCode");

        LabelTextDto dto = controller.convertToDto(value);

        assertEquals("1", dto.getId());
        assertEquals(5, dto.getAppId());
        assertEquals("testCode", dto.getName());
    }

}