package tech.hiphone.weixin.service.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsApiPaySignReqDTO {

    @JsonProperty("appId")
    private String appId;

    @JsonProperty("timeStamp")
    private String timestamp;

    @JsonProperty("nonceStr")
    private String nonceStr;

    @JsonProperty("package")
    private String pkg;

    @JsonProperty("signType")
    private String signType;

    @JsonProperty("paySign")
    private String paySign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    @Override
    public String toString() {
        return "PaySignReqDTO [appId=" + appId + ", timestamp=" + timestamp + ", nonceStr=" + nonceStr + ", pkg=" + pkg
                + ", signType=" + signType + ", paySign=" + paySign + "]";
    }

}
