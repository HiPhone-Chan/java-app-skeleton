package tech.hiphone.weixin.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import tech.hiphone.weixin.domain.id.WxUserId;

public class WeixinAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    private final Object principal;

    private Object credentials;

    /**
     * 
     * @param principal appId
     * @param credentials code
     */
    public WeixinAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public WeixinAuthenticationToken(Object principal, Object credentials,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public String getName() {
        if (this.getPrincipal() instanceof WxUserId) {
            WxUserId wxUserId = (WxUserId) this.getPrincipal();
            return wxUserId.getAppId() + "," + wxUserId.getOpenId();
        }
        return super.getName();
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

}
