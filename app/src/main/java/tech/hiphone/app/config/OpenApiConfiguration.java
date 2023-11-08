package tech.hiphone.app.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import tech.hiphone.framework.config.ProfileConstants;
import tech.hiphone.framework.config.SystemProperties;
import tech.hiphone.framework.config.apidoc.customizer.MyOpenApiCustomizer;

@Configuration
@Profile(ProfileConstants.SPRING_PROFILE_API_DOCS)
public class OpenApiConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "apiFirstGroupedOpenAPI")
    GroupedOpenApi apiFirstGroupedOpenAPI(MyOpenApiCustomizer openApiCustomizer, SystemProperties systemProperties) {
        SystemProperties.ApiDocs properties = systemProperties.getApiDocs();
        return GroupedOpenApi.builder().group("openapi").addOpenApiCustomiser(openApiCustomizer)
                .packagesToScan(AppConstants.BASE_PACKAGE).pathsToMatch(properties.getDefaultIncludePattern()).build();
    }
}
