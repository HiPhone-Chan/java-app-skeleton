package tech.hiphone.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import tech.hiphone.admin_page.config.AdminPageConfiguration;
import tech.hiphone.commons.config.CommonsConfiguration;

@Configuration
@Import({ CommonsConfiguration.class, AdminPageConfiguration.class })
public class AppConfiguration {

}
