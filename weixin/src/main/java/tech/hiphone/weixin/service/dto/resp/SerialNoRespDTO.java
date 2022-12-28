package tech.hiphone.weixin.service.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import tech.hiphone.weixin.service.pay.dto.WeixinPayEncryptedMsg;

public class SerialNoRespDTO {

    @JsonProperty("serial_no")
    private String serialNo;
    @JsonProperty("effective_time")
    private String effectiveTime;
    @JsonProperty("expire_time")
    private String expireTime;
    @JsonProperty("encrypt_certificate")
    private WeixinPayEncryptedMsg encryptCertificate;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public WeixinPayEncryptedMsg getEncryptCertificate() {
        return encryptCertificate;
    }

    public void setEncryptCertificate(WeixinPayEncryptedMsg encryptCertificate) {
        this.encryptCertificate = encryptCertificate;
    }

}
