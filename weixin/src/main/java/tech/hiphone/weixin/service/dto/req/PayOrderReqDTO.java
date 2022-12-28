package tech.hiphone.weixin.service.dto.req;

import tech.hiphone.weixin.config.WeixinProperties;
import tech.hiphone.weixin.config.WeixinProperties.WeixinPayBaseInfo;

public class PayOrderReqDTO extends WeixinPayBaseInfo {

    private String tradeType;
    private String productId;

    public PayOrderReqDTO(WeixinProperties weixinProperties) {
        weixinProperties.super();
    }

    public PayOrderReqDTO(WeixinProperties weixinProperties, WeixinPayBaseInfo weixinPayBaseInfoDTO) {
        weixinProperties.super(weixinPayBaseInfoDTO);
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
