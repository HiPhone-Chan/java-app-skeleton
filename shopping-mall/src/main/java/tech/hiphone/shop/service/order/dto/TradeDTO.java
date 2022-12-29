package tech.hiphone.shop.service.order.dto;

import tech.hiphone.shop.domain.common.Price;

public class TradeDTO {

    private String orderId;

    private Price price;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

}
