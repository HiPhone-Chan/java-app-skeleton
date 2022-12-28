package tech.hiphone.weixin.web.vm;

import javax.validation.constraints.NotNull;

public class RegisterVM {

    @NotNull
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

}
