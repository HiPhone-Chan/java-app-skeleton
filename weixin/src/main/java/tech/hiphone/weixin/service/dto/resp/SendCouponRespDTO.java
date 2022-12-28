package tech.hiphone.weixin.service.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendCouponRespDTO {

    @JsonProperty("return_code")
    private String returnCode;
    @JsonProperty("return_msg")
    private String returnMsg;
    @JsonProperty("appid")
    private String appId;
    @JsonProperty("mch_id")
    private String mchId;
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

    @JsonProperty("resp_count")
    private String respCount;
    @JsonProperty("success_count")
    private String successCount;
    @JsonProperty("failed_count")
    private String failedCount;
    @JsonProperty("openid")
    private String openId;
    @JsonProperty("ret_code")
    private String retCode;
    @JsonProperty("coupon_id")
    private String couponId;
    @JsonProperty("ret_msg")
    private String retMsg;

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

    public String getRespCount() {
        return respCount;
    }

    public void setRespCount(String respCount) {
        this.respCount = respCount;
    }

    public String getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(String successCount) {
        this.successCount = successCount;
    }

    public String getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(String failedCount) {
        this.failedCount = failedCount;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

}
