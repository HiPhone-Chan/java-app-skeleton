package tech.hiphone.weixin.security.authentication;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import tech.hiphone.commons.domain.Authority;
import tech.hiphone.commons.domain.User;
import tech.hiphone.weixin.domain.id.WxUserId;
import tech.hiphone.weixin.service.WxUserService;

// 微信切换应用app认证
public class SwitchWeixinUserAuthenticationProvider implements AuthenticationProvider {

    private final WxUserService wxUserService;

    public SwitchWeixinUserAuthenticationProvider(WxUserService wxUserService) {
        this.wxUserService = wxUserService;
    }

    @Transactional(readOnly = true)
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WxUserId wxUserId = (WxUserId) authentication.getPrincipal();

        User user = wxUserService.getUserWithAuthorities(wxUserId);

        List<GrantedAuthority> authorities = user.getAuthorities().stream().map(Authority::getName)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(user.getLogin(), "", authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (SwitchWeixinAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
