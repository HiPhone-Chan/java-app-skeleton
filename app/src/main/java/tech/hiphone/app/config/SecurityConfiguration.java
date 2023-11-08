package tech.hiphone.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import tech.hiphone.commons.constants.AuthoritiesConstants;
import tech.hiphone.commons.security.jwt.JWTConfigurer;
import tech.hiphone.commons.security.jwt.TokenProvider;
import tech.hiphone.commons.web.filter.OperationLogFilter;
import tech.hiphone.framework.config.SystemProperties;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration {

    private final SystemProperties systemProperties;

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;

    private final OperationLogFilter operationLogFilter;

    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(TokenProvider tokenProvider, CorsFilter corsFilter, SystemProperties systemProperties,
            SecurityProblemSupport problemSupport, OperationLogFilter operationLogFilter) {
        super();
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.operationLogFilter = operationLogFilter;
        this.systemProperties = systemProperties;
        this.problemSupport = problemSupport;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(operationLogFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(problemSupport).accessDeniedHandler(problemSupport)
                .and()
                .headers()
                .contentSecurityPolicy(systemProperties.getSecurity().getContentSecurityPolicy())
                .and()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                .and()
                .permissionsPolicy().policy("camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()")
                .and()
                    .frameOptions().sameOrigin()
                .and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers("/test/**").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/resource/**").permitAll()
                    .antMatchers("/api/authenticate").permitAll()
                    .antMatchers("/api/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    .antMatchers("/api/manager/**").hasAuthority(AuthoritiesConstants.MANAGER)
                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/management/health").permitAll()
                    .antMatchers("/management/health/**").permitAll()
                    .antMatchers("/management/info").permitAll()
                    .antMatchers("/management/prometheus").permitAll()
                    .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
                .and()
                .httpBasic()
                .and()
                .apply(securityConfigurerAdapter());
        return http.build();
        // @formatter:on
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

}
