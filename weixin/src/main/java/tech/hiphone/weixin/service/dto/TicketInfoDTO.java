package tech.hiphone.weixin.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketInfoDTO {

    @JsonProperty("noncestr")
    private String nonceStr;
    @JsonProperty("jsapi_ticket")
    private String jsapiTicket;
    @JsonProperty("timestamp")
    private long timestamp;
    @JsonProperty("url")
    private String url;
    @JsonProperty("signature")
    private String signature;

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "TicketInfoDTO [nonceStr=" + nonceStr + ", jsapiTicket=" + jsapiTicket + ", timestamp=" + timestamp
                + ", url=" + url + ", signature=" + signature + "]";
    }

}
