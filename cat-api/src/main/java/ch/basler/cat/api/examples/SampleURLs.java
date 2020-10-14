package ch.basler.cat.api.examples;

import java.net.URI;

final class SampleURLs {

    private static final String BASE_URL = "http://localhost:8088";

    static final URI DOMAIN_ROUTE = URI.create(BASE_URL + "/domains");
    static final URI CODE_TYPE_ROUTE = URI.create(BASE_URL + "/codetypes");
    static final URI CODE_VALUE_ROUTE = URI.create(BASE_URL + "/codevalues");
    static final URI CODE_TEXT_ROUTE = URI.create(BASE_URL + "/domains/3/codetexts");

    private SampleURLs() {
        // do not instantiate
    }
}
