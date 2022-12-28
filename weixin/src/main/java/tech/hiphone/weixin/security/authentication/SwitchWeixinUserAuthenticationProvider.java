package tech.hiphone.weixin.security.authentication;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.domain.Authority;
import tech.hiphone.commons.domain.User;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.weixin.domain.WxUser;
import tech.hiphone.weixin.domain.id.WxUserId;
import tech.hiphone.weixin.repository.WxUserRepository;

// 微信切换应用app认真
public class SwitchWeixinUserAuthenticationProvider implements AuthenticationProvider {

    private final WxUserRepository wxUserRepository;

    public SwitchWeixinUserAuthenticationProvider(WxUserRepository wxUserRepository) {
        super();
        this.wxUserRepository = wxUserRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WxUserId wxUserId = (WxUserId) authentication.getPrincipal();

        User user = wxUserRepository.findById(wxUserId).map(WxUser::getUser).orElseThrow();

        if (user == null) {
            throw new ServiceException(ErrorCodeContants.USER_NOT_EXISTS);
        }

        List<GrantedAuthority> authorities = user.getAuthorities().stream().map(Authority::getName)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(user.getLogin(), "", authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (SwitchWeixinAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
