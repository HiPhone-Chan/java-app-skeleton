package tech.hiphone.weixin.service.pay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayerDTO {

    @JsonProperty("openid")
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
