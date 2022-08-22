package com.chf.admin_page.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class AdminPageConfiguration implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/page/admin/**").addResourceLocations("classpath:/META-INF/resources/webjars/admin/");
      registry.addResourceHandler("/page/admin").addResourceLocations("classpath:/META-INF/resources/webjars/admin/index.html");
    }

}
