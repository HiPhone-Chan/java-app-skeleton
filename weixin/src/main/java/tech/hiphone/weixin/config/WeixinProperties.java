package tech.hiphone.weixin.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.weixin.constants.WeixinConstants;
import tech.hiphone.weixin.domain.AbstractWxPlatform;
import tech.hiphone.weixin.domain.WxMiniProgram;
import tech.hiphone.weixin.domain.WxOffiaccount;
import tech.hiphone.weixin.domain.WxPay;
import tech.hiphone.weixin.service.pay.dto.WeixinPayEncryptedMsg;
import tech.hiphone.weixin.utils.AesUtil;

@ConfigurationProperties(prefix = "weixin")
public class WeixinProperties {
    // 公众号
    private final Map<String, WeixinOffiaccount> offiaccount = new HashMap<>();
    // 小程序
    private final Map<String, WeixinMiniProgram> miniprogram = new HashMap<>();
    // 商户号
    private final Map<String, WeixinPayBaseInfo> wxpay = new HashMap<>();
    // 开放平台
    private final Map<String, WeixinBaseInfo> oplatform = new HashMap<>();

    // appId : info
    public Map<String, WeixinOffiaccount> getOffiaccount() {
        return offiaccount;
    }

    // appId : info
    public Map<String, WeixinMiniProgram> getMiniprogram() {
        return miniprogram;
    }

    // OrderType : info
    public Map<String, WeixinPayBaseInfo> getWxpay() {
        return wxpay;
    }

    // appId : info
    public Map<String, WeixinBaseInfo> getOplatform() {
        return oplatform;
    }

    public WeixinOffiaccount getOffiaccount(String appId) {
        return Optional
                .ofNullable(Optional.ofNullable(offiaccount.get(appId)).map(WeixinOffiaccount::clone)
                        .orElseGet(() -> miniprogram.get(appId)))
                .map(WeixinOffiaccount::clone)
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot find offiaccount."));
    }

    public WeixinPayBaseInfo getWxPay(String orderType) {
        return Optional.ofNullable(wxpay.get(orderType)).map(WeixinPayBaseInfo::clone)
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot find wxpay info."));
    }

    public static class WeixinBaseInfo implements Cloneable {
        // WeixinConstants
        protected String type;
        private String appId;
        private String appSecret;
        private String openId;
        private String unionId;

        public WeixinBaseInfo() {
        }

        public WeixinBaseInfo(WeixinBaseInfo weixinBaseInfo) {
            super();
            this.type = weixinBaseInfo.getType();
            this.appId = weixinBaseInfo.getAppId();
            this.appSecret = weixinBaseInfo.getAppSecret();
            this.openId = weixinBaseInfo.getOpenId();
            this.unionId = weixinBaseInfo.getUnionId();
        }

        public WeixinBaseInfo(AbstractWxPlatform wxPlatform) {
            this.appId = wxPlatform.getAppId();
            this.appSecret = wxPlatform.getAppSecret();
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getUnionId() {
            return unionId;
        }

        public void setUnionId(String unionId) {
            this.unionId = unionId;
        }

        @Override
        public WeixinBaseInfo clone() {
            try {
                return (WeixinBaseInfo) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return new WeixinBaseInfo(this);
        }
    }

    public class WeixinOffiaccount extends WeixinBaseInfo {

        private String token;

        private String encodingAesKey;

        public WeixinOffiaccount() {
            super.setType(WeixinConstants.TYPE_OFFIACCOUNT);
        }

        public WeixinOffiaccount(WeixinBaseInfo weixinBaseInfo) {
            super(weixinBaseInfo);
            super.setType(WeixinConstants.TYPE_OFFIACCOUNT);
        }

        public WeixinOffiaccount(WxOffiaccount wxOffiaccount) {
            super(wxOffiaccount);
            super.setType(WeixinConstants.TYPE_OFFIACCOUNT);
            this.token = wxOffiaccount.getToken();
            this.encodingAesKey = wxOffiaccount.getEncodingAesKey();
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getEncodingAesKey() {
            return encodingAesKey;
        }

        public void setEncodingAesKey(String encodingAesKey) {
            this.encodingAesKey = encodingAesKey;
        }

        @Override
        public WeixinOffiaccount clone() {
            return (WeixinOffiaccount) super.clone();
        }

    }

    public class WeixinMiniProgram extends WeixinOffiaccount {

        public WeixinMiniProgram() {
            super.setType(WeixinConstants.TYPE_MINIPROGRAM);
        }

        public WeixinMiniProgram(WeixinBaseInfo weixinBaseInfo) {
            super(weixinBaseInfo);
            super.setType(WeixinConstants.TYPE_MINIPROGRAM);
        }

        public WeixinMiniProgram(WeixinOffiaccount weixinPublicInfo) {
            super(weixinPublicInfo);
            super.setType(WeixinConstants.TYPE_MINIPROGRAM);
        }

        public WeixinMiniProgram(WxMiniProgram wxMiniProgram) {
            super(wxMiniProgram);
            super.setType(WeixinConstants.TYPE_MINIPROGRAM);
        }

        @Override
        public WeixinMiniProgram clone() {
            return (WeixinMiniProgram) super.clone();
        }
    }

    public class WeixinPayBaseInfo extends WeixinBaseInfo {

        private String mchId;
        private String mchKey;

        private String callbackUrl;
        // 商户API证书 不是平台证书
        private String serialNo;

        private String apiV3Secret;

        @JsonIgnore
        private final AesUtil aesUtil = new AesUtil();

        public WeixinPayBaseInfo() {
            super.setType(WeixinConstants.TYPE_PAY);
        }

        public WeixinPayBaseInfo(WeixinPayBaseInfo weixinPayBaseInfo) {
            super(weixinPayBaseInfo);
            super.setType(WeixinConstants.TYPE_PAY);
            this.mchId = weixinPayBaseInfo.mchId;
            this.mchKey = weixinPayBaseInfo.mchKey;
            this.callbackUrl = weixinPayBaseInfo.callbackUrl;
            this.serialNo = weixinPayBaseInfo.serialNo;
            this.setApiV3Secret(weixinPayBaseInfo.getApiV3Secret());
        }

        public WeixinPayBaseInfo(WxPay wxPay) {
            super(wxPay);
            super.setType(WeixinConstants.TYPE_PAY);
            this.mchId = wxPay.getMchId();
            this.mchKey = wxPay.getMchKey();
            this.callbackUrl = wxPay.getCallbackUrl();
            this.serialNo = wxPay.getSerialNo();
            this.setApiV3Secret(wxPay.getApiV3Secret());
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
            this.aesUtil.init(apiV3Secret);
        }

        public String decryptToString(WeixinPayEncryptedMsg encryptedMsg) {
            return this.aesUtil.decryptToString(encryptedMsg.getAssociatedData(), encryptedMsg.getNonce(),
                    encryptedMsg.getCiphertext());
        }

        @Override
        public WeixinPayBaseInfo clone() {
            return (WeixinPayBaseInfo) super.clone();
        }

    }
}
