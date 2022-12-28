package tech.hiphone.weixin.constants;

public interface WeixinPayConstants {

    String WEIXIN_MCH_HOST = "https://api.mch.weixin.qq.com";
    // 支付
    String PAY_TRANSACTIONS_URL = "/v3/pay/transactions/";
    // 查询
    String QUERY_PAY_TRANSACTIONS_URL = "/v3/pay/transactions/out-trade-no/";
    // 平台证书
    String CERTIFICATES_URL = "/v3/certificates";
    
    String TOOLS_SHORTURL = WEIXIN_MCH_HOST + "/tools/shorturl";

    String SEND_COUPON_URL = WEIXIN_MCH_HOST + "/mmpaymkttransfers/send_coupon";
    String QUERY_COUPON_URL = WEIXIN_MCH_HOST + "/mmpaymkttransfers/querycouponsinfo";

    String TRADE_TYPE_JSAPI = "jsapi";// 公众号支付, 小程序支付
    String TRADE_TYPE_NATIVE = "native"; // 扫码支付
    String TRADE_TYPE_APP = "app"; // APP支付

    String CHARGE_CALLBACK_PATH = "/charge/callback";
    String PAY_SAN_CALLBACK_PATH = "/pay-scan/callback";

    String SUCCESS = "SUCCESS";
    String FAIL = "FAIL";
}
