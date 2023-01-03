package tech.hiphone.weixin.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import tech.hiphone.weixin.security.authentication.SwitchWeixinUserAuthenticationProvider;
import tech.hiphone.weixin.security.authentication.WeixinAuthenticationProvider;
import tech.hiphone.weixin.service.WeixinService;
import tech.hiphone.weixin.service.WxUserService;

@Configuration("weixinSecurityConfiguration")
public class SecurityConfiguration {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final WeixinService weixinService;

    private final WxUserService wxUserService;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, WeixinService weixinService,
            WxUserService wxUserService) {
        super();
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.weixinService = weixinService;
        this.wxUserService = wxUserService;
    }

    @PostConstruct
    public void init() {
        authenticationManagerBuilder.authenticationProvider(new WeixinAuthenticationProvider(weixinService))
                .authenticationProvider(new SwitchWeixinUserAuthenticationProvider(wxUserService));
    }

}
