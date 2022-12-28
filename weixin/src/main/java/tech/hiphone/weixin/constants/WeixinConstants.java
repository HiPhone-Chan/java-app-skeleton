package tech.hiphone.weixin.constants;

public interface WeixinConstants {

    String WINXIN_HOST = "https://mp.weixin.qq.com";

    String WINXIN_API_HOST = "https://api.weixin.qq.com";

    String WINXIN_OPEN_HOST = "https://open.weixin.qq.com";

    String WINXIN_API_BASE_URL = WINXIN_API_HOST + "/cgi-bin";

    String OAUTH_URL = WINXIN_API_BASE_URL + "/token";

    String SNS_URL = WINXIN_API_HOST + "/sns";

    // ----------------------------------
    // 公众号
    String TYPE_OFFIACCOUNT = "offiaccount";
    // 小程序
    String TYPE_MINIPROGRAM = "miniprogram";
    // 微信支付
    String TYPE_PAY = "wxpay";
    // 开放平台
    String TYPE_OPLATFORM = "oplatform";
    
    // ------------
    String BASE_PACKAGE = "tech.hiphone.weixin";
}
