package tech.hiphone.shop.service.order.dto;

import tech.hiphone.commons.utils.JsonUtil;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.domain.common.Price;

public class OrderInfoDTO {

    private String id;

    // OrderType
    private String type;
    // 外部对应的订单id
    private String outOrderId;
    // OrderChannel
    private String channel;

    private Price price;

    private Integer originalAmount;
    // OrderStatus
    private Integer status;

    private String title;

    private String description;
    // 定价信息
    private PricingDTO pricingInfo;

    private OrderExtraDTO extraInfo;

    private String remark;

    public OrderInfoDTO() {
    }

    public OrderInfoDTO(OrderInfo orderInfo) {
        this.id = orderInfo.getId();
        this.type = orderInfo.getType();
        this.channel = orderInfo.getChannel();
        this.price = orderInfo.getPrice();
        this.originalAmount = orderInfo.getOriginalAmount();
        this.status = orderInfo.getStatus();
        this.title = orderInfo.getTitle();
        this.description = orderInfo.getDescription();
        this.pricingInfo = JsonUtil.readValue(orderInfo.getPricingInfo(), PricingDTO.class);
        this.extraInfo = JsonUtil.readValue(orderInfo.getExtraInfo(), OrderExtraDTO.class);
        this.remark = orderInfo.getRemark();
    }

    public OrderInfo toOrderInfo() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(this.id);
        orderInfo.setType(this.type);
        orderInfo.setChannel(this.channel);
        orderInfo.setPrice(this.price);
        orderInfo.setOriginalAmount(this.originalAmount);
        orderInfo.setStatus(this.status);
        orderInfo.setTitle(this.title);
        orderInfo.setDescription(this.description);
        orderInfo.setPricingInfo(JsonUtil.toJsonString(this.pricingInfo));
        orderInfo.setExtraInfo(JsonUtil.toJsonString(this.extraInfo));
        orderInfo.setRemark(this.remark);
        return orderInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Integer getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Integer originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PricingDTO getPricingInfo() {
        return pricingInfo;
    }

    public void setPricingInfo(PricingDTO pricingInfo) {
        this.pricingInfo = pricingInfo;
    }

    public OrderExtraDTO getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(OrderExtraDTO extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderInfoDTO other = (OrderInfoDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
