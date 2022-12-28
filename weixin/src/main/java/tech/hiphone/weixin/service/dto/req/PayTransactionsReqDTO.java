package tech.hiphone.weixin.service.dto.req;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import tech.hiphone.weixin.service.pay.dto.PayerDTO;

public class PayTransactionsReqDTO {

    @JsonProperty("appid")
    private String appId;

    @JsonProperty("mchid")
    private String mchId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("out_trade_no")
    private String outTradeNo;

    @JsonProperty("time_expire")
    private String timeExpire;

    @JsonProperty("attach")
    private String attach;

    @JsonProperty("notify_url")
    private String notifyUrl;

    @JsonProperty("goods_tag")
    private String goodsTag;

    @JsonProperty("amount")
    private PayTransactionsAmount amount;

    @JsonProperty("payer")
    private PayerDTO payer;

    @JsonProperty("detail")
    private PayTransactionsDetail detail;

    @JsonProperty("scene_info")
    private PayTransactionsSceneInfo sceneInfo;

    @JsonProperty("settle_info")
    private PayTransactionsSettleInfo settleInfo;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public PayTransactionsAmount getAmount() {
        return amount;
    }

    public void setAmount(PayTransactionsAmount amount) {
        this.amount = amount;
    }

    public PayerDTO getPayer() {
        return payer;
    }

    public void setPayer(PayerDTO payer) {
        this.payer = payer;
    }

    public PayTransactionsDetail getDetail() {
        return detail;
    }

    public void setDetail(PayTransactionsDetail detail) {
        this.detail = detail;
    }

    public PayTransactionsSceneInfo getSceneInfo() {
        return sceneInfo;
    }

    public void setSceneInfo(PayTransactionsSceneInfo sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    public PayTransactionsSettleInfo getSettleInfo() {
        return settleInfo;
    }

    public void setSettleInfo(PayTransactionsSettleInfo settleInfo) {
        this.settleInfo = settleInfo;
    }

    // 订单金额信息
    public static class PayTransactionsAmount {
        @JsonProperty("total")
        private int total;
        @JsonProperty("currency")
        private String currency = "CNY";

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

    }

    // 优惠功能
    public static class PayTransactionsDetail {
        @JsonProperty("cost_price")
        private Integer costPrice;
        @JsonProperty("invoice_id")
        private String invoiceId;
        @JsonProperty("goods_detail")
        private List<Object> goodsDetail;

        public Integer getCostPrice() {
            return costPrice;
        }

        public void setCostPrice(Integer costPrice) {
            this.costPrice = costPrice;
        }

        public String getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(String invoiceId) {
            this.invoiceId = invoiceId;
        }

        public List<Object> getGoodsDetail() {
            return goodsDetail;
        }

        public void setGoodsDetail(List<Object> goodsDetail) {
            this.goodsDetail = goodsDetail;
        }

    }

    // 支付场景描述
    public static class PayTransactionsSceneInfo {
        @JsonProperty("payer_client_ip")
        private String payerClientIp;
        @JsonProperty("device_id")
        private String deviceId;
        @JsonProperty("store_info")
        private Object storeInfo;

        public String getPayerClientIp() {
            return payerClientIp;
        }

        public void setPayerClientIp(String payerClientIp) {
            this.payerClientIp = payerClientIp;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public Object getStoreInfo() {
            return storeInfo;
        }

        public void setStoreInfo(Object storeInfo) {
            this.storeInfo = storeInfo;
        }

    }

    // 结算信息
    public static class PayTransactionsSettleInfo {
        @JsonProperty("profit_sharing")
        private boolean profitSharing;

        public boolean getProfitSharing() {
            return profitSharing;
        }

        public void setProfitSharing(boolean profitSharing) {
            this.profitSharing = profitSharing;
        }

    }

}
