package tech.hiphone.app.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import tech.hiphone.framework.config.SystemProperties;
import tech.hiphone.framework.config.ProfileConstants;

@Configuration
@Profile({ ProfileConstants.SPRING_PROFILE_PRODUCTION })
public class StaticResourcesWebConfiguration implements WebMvcConfigurer {

    protected static final String[] RESOURCE_LOCATIONS = new String[] { "classpath:/static/",
            "classpath:/static/content/", "classpath:/static/i18n/", };
    protected static final String[] RESOURCE_PATHS = new String[] { "/*.js", "/*.css", "/*.svg", "/*.png", "*.ico",
            "/content/**", "/i18n/*" };

    private final SystemProperties systemProperties;

    public StaticResourcesWebConfiguration(SystemProperties systemProperties) {
        this.systemProperties = systemProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration resourceHandlerRegistration = appendResourceHandler(registry);
        initializeResourceHandler(resourceHandlerRegistration);
    }

    protected ResourceHandlerRegistration appendResourceHandler(ResourceHandlerRegistry registry) {
        return registry.addResourceHandler(RESOURCE_PATHS);
    }

    protected void initializeResourceHandler(ResourceHandlerRegistration resourceHandlerRegistration) {
        resourceHandlerRegistration.addResourceLocations(RESOURCE_LOCATIONS).setCacheControl(getCacheControl());
    }

    protected CacheControl getCacheControl() {
        return CacheControl.maxAge(getHttpCacheProperty(), TimeUnit.DAYS).cachePublic();
    }

    private int getHttpCacheProperty() {
        return systemProperties.getHttp().getCache().getTimeToLiveInDays();
    }

}
