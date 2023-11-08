package tech.hiphone.commons.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

// 不能直接放到CommonsConfiguration写
@Configuration
public class CommonsSecurityConfiguration {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public CommonsSecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder,
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super();
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() throws Exception {
        DaoAuthenticationConfigurer<AuthenticationManagerBuilder, UserDetailsService> daoAuthenticationConfigurer = new DaoAuthenticationConfigurer<>(
                userDetailsService);
        daoAuthenticationConfigurer.passwordEncoder(passwordEncoder);
        authenticationManagerBuilder.apply(daoAuthenticationConfigurer);
    }
}
