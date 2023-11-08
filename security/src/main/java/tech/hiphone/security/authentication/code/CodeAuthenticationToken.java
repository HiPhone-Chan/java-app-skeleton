package tech.hiphone.security.authentication.code;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

import tech.hiphone.security.authentication.third.ThirdAuthenticationToken;

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

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> getDetails() {
        return (Map<String, String>) super.getDetails();
    }

}
