package tech.hiphone.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import tech.hiphone.app.aop.logging.LoggingAspect;
import tech.hiphone.framework.config.ProfileConstants;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(ProfileConstants.SPRING_PROFILE_DEVELOPMENT)
    LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }

}
