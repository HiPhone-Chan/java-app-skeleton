package tech.hiphone.security.authentication.third;

// 第三方授权
public interface I3rdAuthenticationHandler {

    String HANDLER_SUFFIX = "AuthenticationHandler";

    /**
     * 验证授权code
     * @param authenticationToken
     * @return principal name
     */
    String authorize(ThirdAuthenticationToken authenticationToken);
}
