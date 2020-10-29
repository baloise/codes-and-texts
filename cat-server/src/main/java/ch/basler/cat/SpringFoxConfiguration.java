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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;

import java.util.Collections;
import java.util.List;

@Configuration
public class SpringFoxConfiguration {

    private final List<SecurityScheme> securitySchemes;
    private final List<SecurityContext> securityContexts;

    @Autowired
    public SpringFoxConfiguration(List<SecurityScheme> securitySchemes, List<SecurityContext> securityContexts) {
        this.securitySchemes = securitySchemes;
        this.securityContexts = securityContexts;
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Code and Texts REST API",
                "Management System for I18N codes and texts. If you need more " +
                        "Information, have a look at the " +
                        "<a href=\"https://github.com/baloise/codes-and-texts\">github page</a>",
                "0.0.1",
                "Terms of service",
                new Contact("ACME Inc.", "www.acme.org", "info@acme.org"),
                "Apache 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ch.basler.cat.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes)
                .securityContexts(securityContexts);
    }

    /**
     * SwaggerUI information
     */
    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(true)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }
}
