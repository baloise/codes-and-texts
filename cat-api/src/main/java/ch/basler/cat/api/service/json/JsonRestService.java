package ch.basler.cat.api.service.json;

import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class JsonRestService<T> extends JsonService<T> {

    private final RestTemplate restTemplate = new RestTemplate();

    private final URI url;

    public JsonRestService(URI url, Class<T[]> clazz) {
        super(clazz);
        this.url = url;
    }

    @Override
    protected String getDataAsJson() {
        return restTemplate.getForObject(url, String.class);
    }

}
