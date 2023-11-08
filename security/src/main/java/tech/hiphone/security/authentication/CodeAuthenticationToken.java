package tech.hiphone.security.authentication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class CodeAuthenticationToken extends ThirdAuthenticationToken {

    private static final long serialVersionUID = 1L;

    public CodeAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public CodeAuthenticationToken(String module, Object principal, Object credentials) {
        super(module, principal, credentials);
    }

    public CodeAuthenticationToken(Object principal, Object credentials,
            Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
