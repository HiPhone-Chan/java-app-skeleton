package tech.hiphone.security.authentication;

import org.springframework.security.core.Authentication;

// 第三方授权
public interface I3rdAuthenticate {

    void authorize(Authentication authenticationToken);
}
