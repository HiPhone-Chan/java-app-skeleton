package tech.hiphone.weixin.service.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import tech.hiphone.weixin.constants.WeixinPayConstants;

public class WeixinChargeCallbackRespDTO {

    public static final WeixinChargeCallbackRespDTO SUCCESS = new WeixinChargeCallbackRespDTO(
            WeixinPayConstants.SUCCESS);
    public static final WeixinChargeCallbackRespDTO FAIL = new WeixinChargeCallbackRespDTO(WeixinPayConstants.FAIL);

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    public WeixinChargeCallbackRespDTO() {
    }

    public WeixinChargeCallbackRespDTO(String code) {
        super();
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
