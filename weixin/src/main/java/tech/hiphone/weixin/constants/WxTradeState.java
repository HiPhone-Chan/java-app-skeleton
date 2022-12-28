package tech.hiphone.weixin.constants;

public interface WxTradeState {
    // 支付成功
    String SUCCESS = "SUCCESS";
    // 转入退款
    String REFUND = "REFUND";
    // 未支付
    String NOTPAY = "NOTPAY";
    // 已关闭
    String CLOSED = "CLOSED";
    // 已撤销（仅付款码支付会返回）
    String REVOKED = "REVOKED";
    // 用户支付中（仅付款码支付会返回）
    String USERPAYING = "USERPAYING";
    // 支付失败（仅付款码支付会返回）
    String PAYERROR = "PAYERROR";
}
