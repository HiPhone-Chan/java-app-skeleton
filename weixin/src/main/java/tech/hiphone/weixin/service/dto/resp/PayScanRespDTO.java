package tech.hiphone.weixin.service.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "xml")
public class PayScanRespDTO {

    @JsonProperty("return_code")
    @JacksonXmlCData
    private String returnCode;
    @JsonProperty("return_msg")
    @JacksonXmlCData
    private String returnMsg;
    @JsonProperty("appid")
    @JacksonXmlCData
    private String appId;
    @JsonProperty("mch_id")
    @JacksonXmlCData
    private String mchId;
    @JsonProperty("nonce_str")
    @JacksonXmlCData
    private String nonceStr;
    @JsonProperty("prepay_id")
    @JacksonXmlCData
    private String prepayId;
    @JsonProperty("result_code")
    @JacksonXmlCData
    private String resultCode;
    @JsonProperty("err_code_des")
    @JacksonXmlCData
    private String errCodeDes;
    @JsonProperty("sign")
    @JacksonXmlCData
    private String sign;

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

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
