package ch.basler.cat.api.service.json;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class JsonRestServiceTest {

    private static final URI INVALID_URL = URI.create("http://some.url");

    private JsonRestService<String> service;

    @Before
    public void setUp() throws Exception {
        service = new JsonRestService<>(INVALID_URL, String[].class);
    }

    @Test
    public void getDataAsJson() {
        try {
            service.getDataAsJson();
            fail("Exception expected");
        } catch (Exception e) {
            assertEquals(UnknownHostException.class, e.getCause().getClass());
        }
    }
}