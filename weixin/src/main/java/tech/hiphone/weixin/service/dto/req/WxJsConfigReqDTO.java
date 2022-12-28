package tech.hiphone.weixin.service.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WxJsConfigReqDTO {

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("noncestr")
    private String nonceStr;

    @JsonProperty("url")
    private String url;

    @JsonProperty("jsapi_ticket")
    private String jsApiTicket;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJsApiTicket() {
        return jsApiTicket;
    }

    public void setJsApiTicket(String jsApiTicket) {
        this.jsApiTicket = jsApiTicket;
    }

    @Override
    public String toString() {
        return "WxJsConfigReqDTO [timestamp=" + timestamp + ", nonceStr="
                + nonceStr + ", url=" + url + ", jsApiTicket=" + jsApiTicket
                + "]";
    }

}
