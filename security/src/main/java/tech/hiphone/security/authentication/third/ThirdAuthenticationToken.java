package tech.hiphone.security.authentication.third;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public class ThirdAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    private final String module;

    private final Object principal;

    private final Object credentials;

    public ThirdAuthenticationToken(Object principal, Object credentials) {
        this(null, principal, credentials);
    }

    public ThirdAuthenticationToken(String module, Object principal, Object credentials) {
        super(null);
        this.module = module;
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public ThirdAuthenticationToken(Object principal, Object credentials,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.module = null;
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // must use super, as we override
    }

    public String getModule() {
        return module;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

}
