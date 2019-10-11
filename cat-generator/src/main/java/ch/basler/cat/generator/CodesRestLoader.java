package ch.basler.cat.generator;

import org.springframework.web.client.RestTemplate;

public class CodesRestLoader {

    public static final String SEARCH_ROUTE = "/codeType/search/findByResponsiblePrefix?prefix=";
    public String export(String host, String port, String codesPrefix) {
        final String uri = "http://" + host +":" + port +
                SEARCH_ROUTE + codesPrefix;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }
}
