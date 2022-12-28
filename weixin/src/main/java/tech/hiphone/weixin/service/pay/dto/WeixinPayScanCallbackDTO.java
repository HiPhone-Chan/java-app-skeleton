package tech.hiphone.weixin.service.pay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeixinPayScanCallbackDTO {

    @JsonProperty("appid")
    private String appId;
    @JsonProperty("openid")
    private String openId;
    @JsonProperty("mch_id")
    private String mchId;
    @JsonProperty("is_subscribe")
    private String isSubscribe;
    @JsonProperty("nonce_str")
    private String nonceStr;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("sign")
    private String sign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
