package tech.hiphone.commons.service;

import java.util.Map;

// 验证码
public interface ICodeHandler {

    String HANDLER_SUFFIX = "CodeHandler";

    boolean check(String principal);

    /**
     *  发送验证码
     * @param principal
     * @param code 验证码
     * @param params 其他参数
     */
    void send(String principal, String code, Map<String, String> params);
}
