package ch.basler.cat.client.backend;

import ch.basler.cat.client.backend.mock.MockDataService;
import org.junit.Before;

/**
 * Simple unit test for the back-end data service.
 */
public class DataServiceTest {

    private DataService service;

    @Before
    public void setUp() throws Exception {
        service = MockDataService.getInstance();
    }

}
