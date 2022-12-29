package tech.hiphone.shop.service.order.dto;

import tech.hiphone.shop.domain.common.Price;

// 计价信息
public class PricingDTO {
    // PricingType
    private String type;

    private Price price;

    private Object data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
