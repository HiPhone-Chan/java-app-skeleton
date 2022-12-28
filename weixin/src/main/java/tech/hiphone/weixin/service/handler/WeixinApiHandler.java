package tech.hiphone.weixin.service.handler;

import static tech.hiphone.weixin.constants.WeixinConstants.SNS_URL;
import static tech.hiphone.weixin.constants.WeixinConstants.WINXIN_API_BASE_URL;
import static tech.hiphone.weixin.constants.WeixinConstants.WINXIN_API_HOST;
import static tech.hiphone.weixin.constants.WeixinConstants.WINXIN_HOST;

import java.io.File;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.commons.utils.CharsetUtil;
import tech.hiphone.commons.utils.JsonUtil;
import tech.hiphone.weixin.config.WeixinProperties.WeixinBaseInfo;
import tech.hiphone.weixin.service.dto.req.CustomMessageReqDTO;
import tech.hiphone.weixin.service.dto.req.QrcodeReqDTO;
import tech.hiphone.weixin.service.dto.req.WxaCodeReqDTO;
import tech.hiphone.weixin.service.dto.resp.AccessTokenRespDTO;
import tech.hiphone.weixin.service.dto.resp.JsApiTicketRespDTO;

@SuppressWarnings("unchecked")
public class WeixinApiHandler {

    private static final Logger log = LoggerFactory.getLogger(WeixinApiHandler.class);

    private final RestTemplate restTemplate;

    public WeixinApiHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AccessTokenRespDTO getBaseAccessToken(WeixinBaseInfo weixinBaseInfo) {
        StringBuffer sb = new StringBuffer(WINXIN_API_BASE_URL);
        sb.append("/token?grant_type=client_credential&appid=").append(weixinBaseInfo.getAppId()).append("&secret=")
                .append(weixinBaseInfo.getAppSecret());

        log.debug("Get token req: {}", sb.toString());
        String body = restTemplate.getForObject(sb.toString(), String.class);
        log.debug("Get token resp: {}", body);
        return JsonUtil.readValue(body, AccessTokenRespDTO.class);
    }

    // 公众号登录
    public Map<String, Object> getAccessToken(String code, WeixinBaseInfo weixinBaseInfo) {
        StringBuffer sb = new StringBuffer(SNS_URL);
        sb.append("/oauth2/access_token?appid=").append(weixinBaseInfo.getAppId()).append("&secret=")
                .append(weixinBaseInfo.getAppSecret()).append("&code=").append(code)
                .append("&grant_type=authorization_code");

        String body = restTemplate.getForObject(sb.toString(), String.class);
        log.debug("Get token resp : {}", body);
        return JsonUtil.readValue(body, Map.class);
    }

    // 小程序登录
    public Map<String, Object> getSessionKey(String code, WeixinBaseInfo weixinBaseInfo) {
        StringBuffer sb = new StringBuffer(SNS_URL);
        sb.append("/jscode2session?appid=").append(weixinBaseInfo.getAppId()).append("&secret=")
                .append(weixinBaseInfo.getAppSecret()).append("&js_code=").append(code)
                .append("&grant_type=authorization_code");

        log.debug("Get session key url : {}", sb);
        String body = restTemplate.getForObject(sb.toString(), String.class);
        body = CharsetUtil.convertFromiso_8859_1(body);
        log.debug("Get session key form code : {}", body);
        return JsonUtil.readValue(body, Map.class);
    }

    public JsApiTicketRespDTO getJsApiTicket(String token) {
        StringBuffer sb = new StringBuffer(WINXIN_API_BASE_URL);
        sb.append("/ticket/getticket?access_token=").append(token).append("&type=jsapi");

        String body = restTemplate.getForObject(sb.toString(), String.class);
        body = CharsetUtil.convertFromiso_8859_1(body);
        log.debug("Get jsapi ticket : {}", body);
        return JsonUtil.readValue(body, JsApiTicketRespDTO.class);
    }

    public Map<String, Object> getUserInfo(String token, String openId) {
        StringBuffer sb = new StringBuffer(WINXIN_API_BASE_URL);
        sb.append("/user/info?access_token=").append(token).append("&openid=").append(openId);

        log.debug("Get user info req: {}", sb.toString());
        String body = restTemplate.getForObject(sb.toString(), String.class);
        body = CharsetUtil.convertFromiso_8859_1(body);
        log.debug("Get user info resp : {}", body);
        return JsonUtil.readValue(body, Map.class);
    }

    // 小程序 生成小程序码
    public byte[] generateWxaCode(String token, WxaCodeReqDTO wxaCodeReqDTO) {
        StringBuffer sb = new StringBuffer(WINXIN_API_HOST);
        sb.append("/wxa/getwxacodeunlimit?access_token=").append(token);

        byte[] body = restTemplate.postForObject(sb.toString(), JsonUtil.toJsonString(wxaCodeReqDTO), byte[].class);
        log.debug("Get wxa info : {}", body);
        return body;
    }

    // 小程序 获取手机号码
    public Map<String, Object> getPhoneNumber(String accessToken, String code) {
        StringBuffer sb = new StringBuffer(WINXIN_API_HOST);
        sb.append("/wxa/business/getuserphonenumber?access_token=").append(accessToken).append("&code=").append(code);

        log.debug("Get phone number url : {}", sb);
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        String body = restTemplate.postForObject(sb.toString(), JsonUtil.toJsonString(map), String.class);
        body = CharsetUtil.convertFromiso_8859_1(body);
        log.debug("Get phone number form code : {}", body);
        return JsonUtil.readValue(body, Map.class);
    }

    // 公众号 用户信息
    public Map<String, Object> getSnsUserInfo(String token, String openId) {
        StringBuffer sb = new StringBuffer(SNS_URL);
        sb.append("/userinfo?access_token=").append(token).append("&openid=").append(openId).append("&lang=zh_CN");

        log.debug("Get user info req: {}", sb.toString());
        String body = restTemplate.getForObject(sb.toString(), String.class);
        log.debug("Get user info resp : {}", body);
        return JsonUtil.readValue(body, Map.class);
    }

    public byte[] generateQrcode(String ticket) {
        StringBuffer sb = new StringBuffer(WINXIN_HOST);
        sb.append("/cgi-bin/showqrcode?ticket=").append(ticket);

        byte[] body = restTemplate.getForObject(sb.toString(), byte[].class);
        log.debug("Get qrcode info : {}", body);
        return body;
    }

    // 账号管理 生成二维码
    public Map<String, Object> generateQrcode(String token, QrcodeReqDTO qrcodeReqDTO) {
        StringBuffer sb = new StringBuffer(WINXIN_API_BASE_URL);
        sb.append("/qrcode/create?access_token=").append(token);

        String body = restTemplate.postForObject(sb.toString(), JsonUtil.toJsonString(qrcodeReqDTO), String.class);
        log.debug("Get recode result : {}", body);
        return JsonUtil.readValue(body, Map.class);
    }

    // 客服接口-发消息
    public void sendCustomMessage(String token, CustomMessageReqDTO customMessageReqDTO) {
        StringBuffer sb = new StringBuffer(WINXIN_API_BASE_URL);
        sb.append("/message/custom/send?access_token=").append(token);

        String reqBody = JsonUtil.toJsonString(customMessageReqDTO);
        reqBody = CharsetUtil.convert2iso_8859_1(reqBody);
        String body = restTemplate.postForObject(sb.toString(), reqBody, String.class);
        log.debug("sendCustomMessage result : {}", body);
    }

    // 内容安全
    // 图片安全检查
    public Map<String, Object> imgSecCheck(String token, File file) {
        StringBuffer sb = new StringBuffer(WINXIN_API_HOST);
        sb.append("/wxa/img_sec_check?access_token=").append(token);

        MultiValueMap<String, Object> formBody = new LinkedMultiValueMap<>();
        formBody.add("media", new FileSystemResource(file));
        String body = restTemplate.postForObject(sb.toString(), formBody, String.class);
        log.debug("imgSecCheck result : {}", body);
        return JsonUtil.readValue(body, Map.class);
    }

    // 媒体安全检查
    /**
     * 
     * @param token
     * @param mediaUrl  要检测的多媒体url
     * @param mediaType 1:音频;2:图片
     * @return
     */
    public Map<String, Object> mediaSecCheck(String token, String mediaUrl, int mediaType) {
        StringBuffer sb = new StringBuffer(WINXIN_API_HOST);
        sb.append("/wxa/media_check_async?access_token=").append(token);

        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("media_url", mediaUrl);
        reqBody.put("media_type", mediaType);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(reqBody, headers);

        String body = restTemplate.postForObject(sb.toString(), request, String.class);
        log.info("mediaSecCheck result : {}", body);
        return JsonUtil.readValue(body, Map.class);
    }

    // 文本安全检查
    public Map<String, Object> msgSecCheck(String token, String content) {
        StringBuffer sb = new StringBuffer(WINXIN_API_HOST);
        sb.append("/wxa/msg_sec_check?access_token=").append(token);

        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("content", content);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(reqBody, headers);

        String body = restTemplate.postForObject(sb.toString(), request, String.class);
        log.debug("msgSecCheck result : {}", body);
        return JsonUtil.readValue(body, Map.class);
    }

    // 素材管理-新增永久素材
    public void addMaterial(String token, String type, File file) {
        StringBuffer sb = new StringBuffer(WINXIN_API_BASE_URL);
        sb.append("/material/add_material?access_token=").append(token).append("&type=").append(type);

        HttpHeaders headers = new HttpHeaders();
        headers.add("filename", file.getName());

        MultiValueMap<String, Object> formEntity = new LinkedMultiValueMap<>();
        // formEntity.add("name", "media");
        formEntity.add("media", new FileSystemResource(file));

        HttpEntity<?> entity = new HttpEntity<>(formEntity, headers);
        String body = restTemplate.postForObject(sb.toString(), entity, String.class);
        log.info("Get add material : {}", body);
    }

    // 素材管理-获取素材列表
    public void getMaterials(String token, String type) {
        StringBuffer sb = new StringBuffer(WINXIN_API_BASE_URL);
        sb.append("/material/batchget_material?access_token=").append(token);

        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("offset", 0);
        map.put("count", 20);
        String body = restTemplate.postForObject(sb.toString(), JsonUtil.toJsonString(map), String.class);
        log.info("Get materials : {}", CharsetUtil.convertFromiso_8859_1(body));
    }

    // 自定义菜单
    public void createMenu(String token, String menuInfo) {
        StringBuffer sb = new StringBuffer(WINXIN_API_BASE_URL);
        sb.append("/menu/create?access_token=").append(token);
        menuInfo = CharsetUtil.convert2iso_8859_1(menuInfo);
        String body = restTemplate.postForObject(sb.toString(), menuInfo, String.class);
        log.info("Get create result : {}", body);
    }

    // 小程序解密
    public static String decrypt(String sessionKey64, String encryptedData64, String iv64) {
        try {
            Decoder base64Decoder = Base64.getDecoder();
            byte[] sessionKey = base64Decoder.decode(sessionKey64);
            byte[] encryptedData = base64Decoder.decode(encryptedData64);
            byte[] iv = base64Decoder.decode(iv64);

            SecretKeySpec skeySpec = new SecretKeySpec(sessionKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivp = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivp);
            byte[] data = cipher.doFinal(encryptedData);
            return new String(data);
        } catch (Exception e) {
            log.info("mini app decrypt failed", e);
        }
        throw new ServiceException(ErrorCodeContants.DECODE_FAIL, "Decrypt user info failed.");
    }

    public static String generateNonceStr() {
        return RandomStringUtils.randomAlphanumeric(32);
    }

}
