package tech.hiphone.weixin.service.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "xml")
public class SendCouponReqDTO {

    @JsonProperty("coupon_stock_id")
    private String couponStockId;
    @JsonProperty("openid_count")
    private Integer openIdCount = 1;
    @JsonProperty("partner_trade_no")
    private String partnerTradeNo;
    @JsonProperty("openid")
    private String openId;
    @JsonProperty("appid")
    private String appId;
    @JsonProperty("mch_id")
    private String mchId;
    @JsonProperty("op_user_id")
    private String opUserId;
    @JsonProperty("device_info")
    private String deviceInfo;
    @JsonProperty("nonce_str")
    private String nonceStr;
    @JsonProperty("sign")
    private String sign;
    @JsonProperty("version")
    private String version;
    @JsonProperty("type")
    private String type;

    public String getCouponStockId() {
        return couponStockId;
    }

    public void setCouponStockId(String couponStockId) {
        this.couponStockId = couponStockId;
    }

    public Integer getOpenIdCount() {
        return openIdCount;
    }

    public void setOpenIdCount(Integer openIdCount) {
        this.openIdCount = openIdCount;
    }

    public String getPartnerTradeNo() {
        return partnerTradeNo;
    }

    public void setPartnerTradeNo(String partnerTradeNo) {
        this.partnerTradeNo = partnerTradeNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
