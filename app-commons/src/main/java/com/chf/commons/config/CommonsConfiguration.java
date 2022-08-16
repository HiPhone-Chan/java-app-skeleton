package com.chf.commons.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.chf.commons.constants.CommonsConstants;

@Configuration
@ComponentScan(CommonsConstants.BASE_PACKAGE)
@EnableJpaRepositories(basePackages = CommonsConstants.BASE_PACKAGE + ".repository")
@EntityScan({ CommonsConstants.BASE_PACKAGE })
public class CommonsConfiguration {

}
