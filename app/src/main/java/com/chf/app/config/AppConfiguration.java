package com.chf.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.chf.commons.config.CommonsConfiguration;

@Configuration
@Import({ CommonsConfiguration.class })
public class AppConfiguration {

}
