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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
import java.util.Collections;
import java.util.List;

@Configuration
@ConditionalOnProperty(
        value = "security.type",
        havingValue = "keycloak",
        matchIfMissing = true
)
public class OAuthSwaggerSecurityConfiguration {


    public static final String SECURITY_SCHEME_NAME = "spring_oauth";

    @Value("${security.swagger.keycloak.realm-uri}")
    private String host;
    @Value("${security.swagger.keycloak.client-id}")
    private String clientId;
    @Value("${security.swagger.keycloak.client-secret}")
    private String clientSecret;

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    @Bean
    public List<SecurityScheme> securitySchemes() {

        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint(host + "/protocol/openid-connect/token", "oauthtoken"))
                .tokenRequestEndpoint(
                        new TokenRequestEndpoint(host + "/protocol/openid-connect/auth", clientId.trim(), clientSecret.trim()))
                .build();

        SecurityScheme oauth = new OAuthBuilder().name(SECURITY_SCHEME_NAME)
                .grantTypes(Arrays.asList(grantType))
                .build();
        return Collections.singletonList(oauth);
    }

    @Bean
    public List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(
                        Arrays.asList(SecurityReference.builder().reference(SECURITY_SCHEME_NAME).scopes(new AuthorizationScope[0]).build())
                )
                .forPaths(PathSelectors.any())
                .build());
    }
}
