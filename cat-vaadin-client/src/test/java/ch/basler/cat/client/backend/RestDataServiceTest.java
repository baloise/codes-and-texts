package ch.basler.cat.client.backend;

import ch.basler.cat.client.backend.data.Application;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class RestDataServiceTest {

    @Test
    @Ignore
    public void getAllApplications() {
        Collection<Application> allApplications = RestDataService.getInstance().getAllApplications();
        assertNotNull(allApplications);

    }

    @Test
    public void combine() {
        RestDataService restDataService = new RestDataService();
        String routeLevel1 = restDataService.combine("group", 1, "element");
        assertEquals("group/1/element", routeLevel1);
        String routeLevel2 = restDataService.combine(routeLevel1, 2);
        assertEquals("group/1/element/2", routeLevel2);
    }
}