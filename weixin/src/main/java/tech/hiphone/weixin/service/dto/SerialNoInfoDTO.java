package tech.hiphone.weixin.service.dto;

import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tech.hiphone.weixin.utils.PemUtil;

public class SerialNoInfoDTO {

    private String serialNo;
    private Instant effectiveTime;
    private Instant expireTime;

    @JsonIgnore
    private Certificate certificate;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Instant getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Instant effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Instant getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Instant expireTime) {
        this.expireTime = expireTime;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public void setCertificate(String certificateStr) {
        this.certificate = PemUtil.loadCertificate(new ByteArrayInputStream(certificateStr.getBytes()));
    }

}
