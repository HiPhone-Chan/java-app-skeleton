package tech.hiphone.weixin.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import tech.hiphone.weixin.repository.WxUserRepository;
import tech.hiphone.weixin.security.authentication.SwitchWeixinUserAuthenticationProvider;
import tech.hiphone.weixin.security.authentication.WeixinAuthenticationProvider;
import tech.hiphone.weixin.service.WeixinService;

@Configuration("weixinSecurityConfiguration")
public class SecurityConfiguration {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final WxUserRepository wxUserRepository;

    private final WeixinService weixinService;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder,
            WxUserRepository wxUserRepository, WeixinService weixinService) {
        super();
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.wxUserRepository = wxUserRepository;
        this.weixinService = weixinService;
    }

    @PostConstruct
    public void init() {
        authenticationManagerBuilder.authenticationProvider(new WeixinAuthenticationProvider(weixinService))
                .authenticationProvider(new SwitchWeixinUserAuthenticationProvider(wxUserRepository));
    }

}
