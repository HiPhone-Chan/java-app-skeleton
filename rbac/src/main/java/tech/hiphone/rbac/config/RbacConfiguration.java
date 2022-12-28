package tech.hiphone.rbac.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import tech.hiphone.framework.jpa.support.JpaExtRepositoryFactoryBean;
import tech.hiphone.rbac.constants.RbacConstants;

@Configuration
@ComponentScan(RbacConstants.BASE_PACKAGE)
@EnableJpaRepositories(basePackages = RbacConstants.BASE_PACKAGE
        + ".repository", repositoryFactoryBeanClass = JpaExtRepositoryFactoryBean.class)
@EntityScan({ RbacConstants.BASE_PACKAGE })
public class RbacConfiguration {

    public RbacConfiguration() {
        super();
    }

}
