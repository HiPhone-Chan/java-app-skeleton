package tech.hiphone.framework.config.apidoc;

import static tech.hiphone.framework.config.ProfileConstants.SPRING_PROFILE_API_DOCS;

import org.springdoc.core.SpringDocConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import tech.hiphone.framework.config.SystemProperties;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(OpenAPI.class)
@Profile(SPRING_PROFILE_API_DOCS)
@AutoConfigureBefore(SpringDocConfiguration.class)
@AutoConfigureAfter(SystemProperties.class)
@Import(SpringDocGroupsConfiguration.class)
public class SpringDocAutoConfiguration {

    public SpringDocAutoConfiguration() {
    }

}
