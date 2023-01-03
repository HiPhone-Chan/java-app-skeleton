package tech.hiphone.shop.service.order.dto;

public class GoodsInfoDTO {

    private String type;

    private Long goodsId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

}
