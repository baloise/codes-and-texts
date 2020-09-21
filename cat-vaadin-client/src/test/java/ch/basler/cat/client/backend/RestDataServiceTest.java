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
}