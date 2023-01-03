package tech.hiphone.shop.service.order.dto;

import tech.hiphone.shop.domain.common.Price;

// 计价信息
public class PricingDTO {

    private Price price;

    private GoodsInfoDTO goodsInfo;

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public GoodsInfoDTO getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfoDTO goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

}
