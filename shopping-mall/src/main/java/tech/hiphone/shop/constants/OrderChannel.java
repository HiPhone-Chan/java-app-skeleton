package tech.hiphone.shop.constants;

//支付使用的第三方支付渠道
public interface OrderChannel {

    String alipay = "alipay"; // 支付宝 App 支付
    String alipay_wap = "alipay_wap"; // 支付宝手机网站支付
    String alipay_qr = "alipay_qr"; // 支付宝扫码支付
    String alipay_scan = "alipay_scan"; // 支付宝条码支付
    String alipay_lite = "alipay_lite"; // 支付宝小程序支付
    String alipay_pc_direct = "alipay_pc_direct"; // 支付宝电脑网站支付
    String wx = "wx"; // 微信 App 支付
    String wx_pub = "wx_pub"; // 微信 JSAPI 支付
    String wx_pub_qr = "wx_pub_qr"; // 微信 Native 支付
    String wx_pub_scan = "wx_pub_scan"; // 微信付款码支付
    String wx_wap = "wx_wap"; // 微信 H5 支付
    String wx_lite = "wx_lite"; // 微信小程序支付
    String qpay = "qpay"; // QQ 钱包 App 支付
    String qpay_pub = "qpay_pub"; // QQ 钱包公众号支付
    String upacp = "upacp"; // 银联手机控件支付（银联 App 支付）
    String upacp_pc = "upacp_pc"; // 银联网关支付（银联 PC 网页支付）
    String upacp_qr = "upacp_qr"; // 银联二维码（主扫）
    String upacp_scan = "upacp_scan"; // 银联二维码（被扫）
    String upacp_wap = "upacp_wap"; // 银联手机网站支付
    String upacp_b2b = "upacp_b2b"; // 银联企业网银支付（银联 B2B PC 网页支付）
    String cp_b2b = "cp_b2b"; // 银联电子企业网银支付（银联电子 B2B PC 网页支付）
    String applepay_upacp = "applepay_upacp"; // Apple Pay
    String cmb_wallet = "cmb_wallet"; // 招行一网通
    String cmb_pc_qr = "cmb_pc_qr"; // 招行 PC 扫码支付
    String bfb_wap = "bfb_wap"; // 百度钱包
    String jdpay_wap = "jdpay_wap"; // 京东支付
    String yeepay_wap = "yeepay_wap"; // 易宝支付
    String isv_qr = "isv_qr"; // 线下扫码（主扫）
    String isv_scan = "isv_scan"; // 线下扫码（被扫）
    String isv_wap = "isv_wap"; // 线下扫码（固定码）
    String isv_lite = "isv_lite"; // 线下小程序支付
    String ccb_pay = "ccb_pay"; // 建行 App 支付
    String ccb_qr = "ccb_qr"; // 建行二维码支付
    String cmpay = "cmpay"; // 移动和包支付
    String coolcredit = "coolcredit"; // 库分期
    String cb_alipay = "cb_alipay"; // 跨境支付宝 App 支付
    String cb_alipay_wap = "cb_alipay_wap"; // 跨境支付宝手机网站支付
    String cb_alipay_qr = "cb_alipay_qr"; // 跨境支付宝扫码支付
    String cb_alipay_scan = "cb_alipay_scan"; // 跨境支付宝条码支付
    String cb_alipay_pc_direct = "cb_alipay_pc_direct"; // 跨境支付宝电脑网站支付
    String cb_wx = "cb_wx"; // 跨境微信 App 支付
    String cb_wx_pub = "cb_wx_pub"; // 跨境微信 JSAPI 支付
    String cb_wx_pub_qr = "cb_wx_pub_qr"; // 跨境微信 Native 支付
    String cb_wx_pub_scan = "cb_wx_pub_scan"; // 跨境微信付款码支付
    String paypal = "paypal"; // PayPal
    String balance = "balance"; // 余额
    // ----------------
    String off_line = "off_line"; // 线下
    String sys_application = "sys_application"; // 系统自身应用
}
