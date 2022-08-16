package com.chf.app.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.chf.commons.config.CommonsConfiguration;

@Configuration
@EnableConfigurationProperties({ ApplicationProperties.class })
@Import({ CommonsConfiguration.class })
public class AppConfiguration {

}
