package tech.hiphone.shop.service.order.dto;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import tech.hiphone.shop.constants.Currency;

public class ChargeInfoDTO {

    private String orderId;

    private String appId;

    // OrderChannel
    @NotNull
    private String channel; // 支付使用的第三方支付渠道

    private String clientIp; // 发起支付请求终端的 IP 地址

    private String currency = Currency.CNY; // 三位 ISO 货币代码

    // 订单总金额, 单位为对应币种的最小货币单位，例如：人民币为分（如订单总金额为 1元，此处请填100
    private Integer amount;

    private String title;

    private String description;

    private String couponId;
    // 特定渠道发起交易时需要的额外参数
    private Map<String, String> extra = new HashMap<>();

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }

}
