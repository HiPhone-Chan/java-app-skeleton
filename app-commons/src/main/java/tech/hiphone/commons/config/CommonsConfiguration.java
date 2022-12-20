package tech.hiphone.commons.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import tech.hiphone.commons.constants.CommonsConstants;
import tech.hiphone.framework.jpa.support.JpaExtRepositoryFactoryBean;

@Configuration
@EnableConfigurationProperties({ ApplicationProperties.class })
@ComponentScan(CommonsConstants.BASE_PACKAGE)
@EnableJpaRepositories(basePackages = CommonsConstants.BASE_PACKAGE
        + ".repository", repositoryFactoryBeanClass = JpaExtRepositoryFactoryBean.class)
@EntityScan({ CommonsConstants.BASE_PACKAGE })
public class CommonsConfiguration {

}
