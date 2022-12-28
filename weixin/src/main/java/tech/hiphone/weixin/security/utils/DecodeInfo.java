package tech.hiphone.weixin.security.utils;

public class DecodeInfo {

    private String appId;

    private String content;

    public DecodeInfo(String appId, String content) {
        super();
        this.appId = appId;
        this.content = content;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
