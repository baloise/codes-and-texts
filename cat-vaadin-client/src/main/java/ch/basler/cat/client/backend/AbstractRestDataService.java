package ch.basler.cat.client.backend;

import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class AbstractRestDataService {

    private static final String HOST = "localhost";
    private static final String PORT = "8088";
    private static final String URL_PREFIX = "http://" + HOST + ":" + PORT;
    private final Gson gson = new Gson();
    private final RestTemplate restTemplate = new RestTemplate();

    protected <T> Collection<T> getAllData(String route, Class<T[]> clazz) {
        String uri = combine(URL_PREFIX, route);
        String response = restTemplate.getForObject(uri, String.class);
        return Arrays.asList(gson.fromJson(response, clazz));
    }

    protected <I,T> T getById(String route, I id, Class<T> clazz) {
        final String uri = combine(URL_PREFIX, route, id);
        String response = restTemplate.getForObject(uri, String.class);
        return gson.fromJson(response, clazz);
    }

    protected <T> void createData(String route, T data, Class<T> clazz) {
        final String uri = combine(URL_PREFIX, route);
        restTemplate.postForObject(uri, data, clazz);
    }

    protected <I,T> void updateData(String route, I id, T data) {
        String uri = combine(URL_PREFIX, route, id);
        restTemplate.put(uri, data);
    }

    protected <I> void deleteData(String route, I id) {
        String uri = combine(URL_PREFIX, route, id);
        restTemplate.delete(uri);
    }

    protected String combine(Object... items) {
        return Arrays.stream(items).map(Object::toString).collect(Collectors.joining("/"));
    }
}
