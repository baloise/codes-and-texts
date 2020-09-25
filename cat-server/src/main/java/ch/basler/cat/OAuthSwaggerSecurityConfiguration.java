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
package ch.basler.cat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.Arrays;

@Configuration
public class OAuthSwaggerSecurityConfiguration {


    //@Value("${host}")
    private String host = "https://keycloak-okd4-sampleconfig.apps.okd.baloise.dev";
    private final String CLIENT_ID = "cat-server";
    private final String CLIENT_SECRET = "8e9a6b52-ef2a-458e-9be3-feb7a4e494f9";
    private final String AUTH_SERVER_AUTHORIZE = "https://keycloak-okd4-sampleconfig.apps.okd.baloise.dev/auth/realms/workshop/protocol/openid-connect/auth";
    private final String AUTH_SERVER_TOKEN = "https://keycloak-okd4-sampleconfig.apps.okd.baloise.dev/auth/realms/workshop/protocol/openid-connect/token";

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    @Bean
    public SecurityScheme securityScheme() {

        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint(AUTH_SERVER_TOKEN, "oauthtoken"))
                .tokenRequestEndpoint(
                        new TokenRequestEndpoint(AUTH_SERVER_AUTHORIZE, CLIENT_ID, CLIENT_SECRET))
                .build();

        //GrantType grantType = new ClientCredentialsGrant(token_endpoint);
        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
                .grantTypes(Arrays.asList(grantType))
                .build();
        return oauth;
    }

    @Bean
    public SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        Arrays.asList(SecurityReference.builder().reference("spring_oauth").scopes(new AuthorizationScope[0]).build())
                )
                .forPaths(PathSelectors.any())
                .build();
    }
}