package tech.hiphone.security.authentication;

import org.springframework.security.core.Authentication;

// 验证码
public interface IUserCodeHandler {

    boolean check(String principal);

    // 管理员创建用户时要做的事
    default void createUser(Authentication authenticationToken) {
    };

    // 注册时要做的事
    void register(Authentication authenticationToken);

    // 忘记密码时要做的事
    void requestPasswordReset(Authentication authenticationToken);

}
