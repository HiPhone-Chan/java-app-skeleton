package tech.hiphone.weixin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// 微信支付平台
@Entity
@Table(name = "wx_pay")
public class WxPay extends AbstractWxPlatform {

    private static final long serialVersionUID = 1L;

    @Column(name = "mch_id", length = 31)
    private String mchId;

    // 新版没用
    @Column(name = "mch_key", length = 31)
    private String mchKey;

    @Column(name = "callback_url", length = 255)
    private String callbackUrl;
    // 商户API证书 不是平台证书
    @Column(name = "serial_no", length = 63)
    private String serialNo;

    @Column(name = "api_v3_secret", length = 63)
    private String apiV3Secret;

    // OrderType
    @Column(name = "order_type", nullable = false, length = 31)
    private String orderType;

    public WxPay toWxPay() {
        WxPay wxPay = new WxPay();
        wxPay.setAppSecret(getAppSecret());
        wxPay.setName(getName());
        wxPay.setDescription(getDescription());
        wxPay.setMchId(mchId);
        wxPay.setMchKey(mchKey);
        wxPay.setCallbackUrl(callbackUrl);
        wxPay.setSerialNo(serialNo);
        wxPay.setApiV3Secret(apiV3Secret);
        return wxPay;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getApiV3Secret() {
        return apiV3Secret;
    }

    public void setApiV3Secret(String apiV3Secret) {
        this.apiV3Secret = apiV3Secret;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public WxPay() {
    }

    public WxPay(AbstractWxPlatform abstractWxPlatform) {
        super(abstractWxPlatform);
    }

    @Override
    public WxPay clone() {
        return (WxPay) super.clone();
    }
}
