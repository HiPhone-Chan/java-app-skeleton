package com.chf.app.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.chf.framework.config.SystemProperties;
import com.chf.framework.config.ProfileConstants;
import com.chf.framework.config.apidoc.customizer.MyOpenApiCustomizer;

@Configuration
@Profile(ProfileConstants.SPRING_PROFILE_API_DOCS)
public class OpenApiConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "apiFirstGroupedOpenAPI")
    public GroupedOpenApi apiFirstGroupedOpenAPI(MyOpenApiCustomizer openApiCustomizer,
            SystemProperties systemProperties) {
        SystemProperties.ApiDocs properties = systemProperties.getApiDocs();
        return GroupedOpenApi.builder().group("openapi").addOpenApiCustomiser(openApiCustomizer)
                .packagesToScan(SystemConstants.BASE_PACKAGE).pathsToMatch(properties.getDefaultIncludePattern())
                .build();
    }
}
