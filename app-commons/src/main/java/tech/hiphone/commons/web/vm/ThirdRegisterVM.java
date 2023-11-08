package tech.hiphone.commons.web.vm;

import javax.validation.constraints.NotNull;

import tech.hiphone.commons.service.dto.AbstractUserDTO;

public class ThirdRegisterVM {

    private String login;

    private String principal;

    @NotNull
    private String module;

    private String appId;

    @NotNull
    private String code;

    private boolean rememberMe;

    public AbstractUserDTO toAbstractUserDTO() {
        AbstractUserDTO userDTO = new AbstractUserDTO() {
        };
        userDTO.setLogin(login);
        userDTO.setLogin(principal);
        return userDTO;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

}
