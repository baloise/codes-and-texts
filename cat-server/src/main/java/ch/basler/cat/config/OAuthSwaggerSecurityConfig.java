/*
 * Copyright 2020 Baloise Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.basler.cat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//@Configuration
public class OAuthSwaggerSecurityConfig {

    //@Value("${host}")
    private String host = "https://keycloak-okd4-sampleconfig.apps.okd.baloise.dev";

    private static final String SECURITY_SCHEME_NAME = "spring_oauth";
    private static final String CLIENT_ID = "spring-security-oauth2-read-write-client";
    private static final String CLIENT_SECRET = "spring-security-oauth2-read-write-client-password1234";

    //@Bean
    public SecurityConfiguration securityInfo() {
        return SecurityConfigurationBuilder.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .scopeSeparator(" ")
                .build();
    }

    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations")
        };

        return scopes;
    }

    private List<SecurityReference> securityReferences() {
        return Collections.singletonList(new SecurityReference(SECURITY_SCHEME_NAME, scopes()));
    }

    //@Bean
    public SecurityScheme securityScheme() {
        LoginEndpoint loginEndpoint = new LoginEndpoint(host + "/oauth/authorize");
        GrantType grantType = new ImplicitGrant(loginEndpoint, "");

        SecurityScheme oauth = new OAuthBuilder().name(SECURITY_SCHEME_NAME)
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();

        return oauth;
    }

    //@Bean
    public SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(securityReferences())
                .build();
    }
}