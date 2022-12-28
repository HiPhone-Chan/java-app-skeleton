package tech.hiphone.weixin.service.pay.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayTransactionsResultDTO {

    @JsonProperty("appid")
    private String appId;

    @JsonProperty("mchid")
    private String mchId;

    @JsonProperty("out_trade_no")
    private String outTradeNo;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("trade_type")
    private String tradeType;

    @JsonProperty("trade_state")
    private String tradeState;

    @JsonProperty("trade_state_desc")
    private String tradeStateDesc;

    @JsonProperty("bank_type")
    private String bankType;

    @JsonProperty("attach")
    private String attach;

    @JsonProperty("success_time")
    private String successTime;

    @JsonProperty("payer")
    private PayerDTO payer;

    @JsonProperty("amount")
    private ChargeCallbackAmount amount;

    @JsonProperty("scene_info")
    private SceneInfo sceneInfo;

    @JsonProperty("promotion_detail")
    private List<Object> promotionDetail;

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

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getTradeStateDesc() {
        return tradeStateDesc;
    }

    public void setTradeStateDesc(String tradeStateDesc) {
        this.tradeStateDesc = tradeStateDesc;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }

    public PayerDTO getPayer() {
        return payer;
    }

    public void setPayer(PayerDTO payer) {
        this.payer = payer;
    }

    public ChargeCallbackAmount getAmount() {
        return amount;
    }

    public void setAmount(ChargeCallbackAmount amount) {
        this.amount = amount;
    }

    public SceneInfo getSceneInfo() {
        return sceneInfo;
    }

    public void setSceneInfo(SceneInfo sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    public List<Object> getPromotionDetail() {
        return promotionDetail;
    }

    public void setPromotionDetail(List<Object> promotionDetail) {
        this.promotionDetail = promotionDetail;
    }

    // 订单金额信息
    public static class ChargeCallbackAmount {
        @JsonProperty("total")
        private int total;
        @JsonProperty("payer_total")
        private int payerTotal;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("payer_currency")
        private String payerCurrency;

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

        public int getPayerTotal() {
            return payerTotal;
        }

        public void setPayerTotal(int payerTotal) {
            this.payerTotal = payerTotal;
        }

        public String getPayerCurrency() {
            return payerCurrency;
        }

        public void setPayerCurrency(String payerCurrency) {
            this.payerCurrency = payerCurrency;
        }
    }

    public static class SceneInfo {
        @JsonProperty("device_id")
        private String deviceId;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

    }

}
