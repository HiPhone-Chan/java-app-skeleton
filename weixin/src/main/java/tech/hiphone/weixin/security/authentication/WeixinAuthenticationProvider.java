package tech.hiphone.weixin.security.authentication;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import tech.hiphone.weixin.constants.WxAuthoritiesConstants;
import tech.hiphone.weixin.domain.id.WxUserId;
import tech.hiphone.weixin.service.WeixinService;
import tech.hiphone.weixin.service.WxUserService;

// 通过微信的code认证
public class WeixinAuthenticationProvider implements AuthenticationProvider {

    private final WeixinService weixinService;

    private final WxUserService wxUserService;

    public WeixinAuthenticationProvider(WeixinService weixinService, WxUserService wxUserService) {
        super();
        this.weixinService = weixinService;
        this.wxUserService = wxUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String appId = (String) authentication.getPrincipal();
        Map<String, Object> result = weixinService.getInfoFromCode((WeixinAuthenticationToken) authentication);
        List<GrantedAuthority> authorities = Arrays.asList(WxAuthoritiesConstants.WEIXIN).stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        String openId = (String) result.get("openid");

        AbstractAuthenticationToken authenticationToken = new WeixinAuthenticationToken(new WxUserId(appId, openId),
                authentication.getCredentials(), authorities);
        authenticationToken.setDetails(result);

        wxUserService.saveWxUser(appId, result);
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (WeixinAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
