package tech.hiphone.weixin.service.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryCouponRespDTO {

    @JsonProperty("return_code")
    private String returnCode;
    @JsonProperty("return_msg")
    private String returnMsg;
    @JsonProperty("appid")
    private String appId;
    @JsonProperty("mch_id")
    private String mchId;
    @JsonProperty("sub_mch_id")
    private String subMchId;
    @JsonProperty("device_info")
    private String deviceInfo;
    @JsonProperty("nonce_str")
    private String nonceStr;
    @JsonProperty("sign")
    private String sign;
    @JsonProperty("result_code")
    private String resultCode;
    @JsonProperty("err_code")
    private String errCode;
    @JsonProperty("err_code_des")
    private String errCodeDes;

    @JsonProperty("coupon_stock_id")
    private String couponStockId;
    @JsonProperty("coupon_id")
    private String couponId;
    @JsonProperty("coupon_value")
    private String couponValue;
    @JsonProperty("coupon_mininum")
    private String couponMininum;
    @JsonProperty("coupon_name")
    private String couponName;
    @JsonProperty("coupon_state")
    private String couponState;
    @JsonProperty("coupon_desc")
    private String couponDesc;
    @JsonProperty("coupon_use_value")
    private String couponUseValue;
    @JsonProperty("coupon_remain_value")
    private String couponRemainValue;
    @JsonProperty("send_source")
    private String sendSource;
    @JsonProperty("is_partial_use")
    private String isPartialUse;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
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

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
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

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public String getCouponStockId() {
        return couponStockId;
    }

    public void setCouponStockId(String couponStockId) {
        this.couponStockId = couponStockId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(String couponValue) {
        this.couponValue = couponValue;
    }

    public String getCouponMininum() {
        return couponMininum;
    }

    public void setCouponMininum(String couponMininum) {
        this.couponMininum = couponMininum;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponState() {
        return couponState;
    }

    public void setCouponState(String couponState) {
        this.couponState = couponState;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public String getCouponUseValue() {
        return couponUseValue;
    }

    public void setCouponUseValue(String couponUseValue) {
        this.couponUseValue = couponUseValue;
    }

    public String getCouponRemainValue() {
        return couponRemainValue;
    }

    public void setCouponRemainValue(String couponRemainValue) {
        this.couponRemainValue = couponRemainValue;
    }

    public String getSendSource() {
        return sendSource;
    }

    public void setSendSource(String sendSource) {
        this.sendSource = sendSource;
    }

    public String getIsPartialUse() {
        return isPartialUse;
    }

    public void setIsPartialUse(String isPartialUse) {
        this.isPartialUse = isPartialUse;
    }

}
