package tech.hiphone.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import tech.hiphone.commons.config.CommonsConfiguration;

@Configuration
@Import({ CommonsConfiguration.class })
public class AppConfiguration {

}
