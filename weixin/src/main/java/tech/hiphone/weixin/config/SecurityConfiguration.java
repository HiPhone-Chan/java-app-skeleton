package tech.hiphone.weixin.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import tech.hiphone.weixin.repository.WxUserRepository;
import tech.hiphone.weixin.security.authentication.SwitchWeixinUserAuthenticationProvider;
import tech.hiphone.weixin.security.authentication.WeixinAuthenticationProvider;
import tech.hiphone.weixin.service.WeixinService;
import tech.hiphone.weixin.service.WxUserService;

@Configuration("weixinSecurityConfiguration")
public class SecurityConfiguration {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final WxUserRepository wxUserRepository;

    private final WeixinService weixinService;

    private final WxUserService wxUserService;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder,
            WxUserRepository wxUserRepository, WeixinService weixinService, WxUserService wxUserService) {
        super();
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.wxUserRepository = wxUserRepository;
        this.weixinService = weixinService;
        this.wxUserService = wxUserService;
    }

    @PostConstruct
    public void init() {
        authenticationManagerBuilder
                .authenticationProvider(new WeixinAuthenticationProvider(weixinService, wxUserService))
                .authenticationProvider(new SwitchWeixinUserAuthenticationProvider(wxUserRepository));
    }

    @Bean("weixinFilterChain")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
            .antMatchers("/api/weixin/authenticate").permitAll()
            ;
        return http.build();
        // @formatter:on
    }

}
