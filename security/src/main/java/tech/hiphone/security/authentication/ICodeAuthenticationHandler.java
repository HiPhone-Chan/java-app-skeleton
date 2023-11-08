package tech.hiphone.security.authentication;

import org.springframework.security.core.Authentication;

// 验证码
public interface ICodeAuthenticationHandler {

    String HANDLER_SUFFIX = "AuthenticationHandler";

    // 绑定账号
    void requestBinding(Authentication authenticationToken);

    // 绑解绑账号
    void requestUnbind(Authentication authenticationToken);
}
