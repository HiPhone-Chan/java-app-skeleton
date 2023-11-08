package tech.hiphone.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import tech.hiphone.framework.config.ProfileUtil;

public class ApplicationWebXml extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        ProfileUtil.addDefaultProfile(application.application());
        return application.sources(Application.class);
    }
}
