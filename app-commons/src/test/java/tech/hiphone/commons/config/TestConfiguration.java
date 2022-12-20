package tech.hiphone.commons.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties({ ApplicationProperties.class })
@Import({ CommonsConfiguration.class })
public class TestConfiguration {

}
