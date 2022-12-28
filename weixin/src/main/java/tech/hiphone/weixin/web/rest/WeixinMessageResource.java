package tech.hiphone.weixin.web.rest;

import java.util.Map;

import javax.annotation.security.PermitAll;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.hiphone.commons.utils.JsonUtil;
import tech.hiphone.weixin.config.WeixinProperties;
import tech.hiphone.weixin.config.WeixinProperties.WeixinOffiaccount;
import tech.hiphone.weixin.security.utils.DecodeInfo;
import tech.hiphone.weixin.security.utils.MessageUtil;
import tech.hiphone.weixin.service.content.ContentCheckService;

/**
 * 接收微信服务器的消息
 * 
 * @author chhfeng
 *
 */
@RestController
@RequestMapping("/api/weixin/message")
public class WeixinMessageResource {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final WeixinProperties weixinProperties;

    private final ContentCheckService contentCheckService;

    public WeixinMessageResource(WeixinProperties weixinProperties, ContentCheckService contentCheckService) {
        super();
        this.weixinProperties = weixinProperties;
        this.contentCheckService = contentCheckService;
    }

    @GetMapping(path = "/push/{type}")
    @PermitAll()
    public String validataePush(@PathVariable("type") String type, @RequestParam String signature,
            @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr) {
        log.info("REST request to push validate {} : {} - {} - {} - {}", type, signature, timestamp, nonce, echostr);
        try {
            WeixinOffiaccount info = weixinProperties.getOffiaccount(type);

            String sign = MessageUtil.sha1Encode(info.getToken(), timestamp, nonce);
            if (signature.equals(sign)) {
                log.info("validate success: " + echostr);
                return echostr;
            }
        } catch (Exception e) {
            log.warn("Validate server sign wrong.", e);
        }
        return "";
    }

    public final static String SUCCESS_RESP = "success";

    @PostMapping(path = "/push/{type}", produces = { MediaType.TEXT_PLAIN_VALUE })
    @PermitAll()
    public String push(@PathVariable("type") String type, @RequestParam String signature,
            @RequestParam String timestamp, @RequestParam String nonce,
            @RequestParam(name = "msg_signature", required = false) String msgSignature, @RequestBody String body) {
        log.debug("REST request to push validate {} : {} - {} - {}", type, signature, timestamp, nonce);
        log.debug("REST request to push body : {}", body);

        WeixinOffiaccount info = weixinProperties.getOffiaccount(type);
        String token = info.getToken();
        String sign = MessageUtil.sha1Encode(token, timestamp, nonce);
        if (signature.equals(sign)) {
            Map<String, Object> weixinPushMsg = JsonUtil.readMapValue(body);
            String encrypt = (String) weixinPushMsg.get("Encrypt");
            if (!StringUtils.isEmpty(msgSignature)) {
                String msgSign = MessageUtil.sha1Encode(token, timestamp, nonce, encrypt);
                if (!msgSignature.equals(msgSign)) {
                    log.warn("Msg signature sign wrong.");
                    return "";
                }
            }

            String appId = info.getAppId();
            if (!StringUtils.isEmpty(encrypt)) {
                DecodeInfo decryptInfo = MessageUtil.aesDecrypt(info.getEncodingAesKey(), encrypt);
                String content = decryptInfo.getContent();
                log.debug("Decode to content : {}", content);
                if (!decryptInfo.getAppId().equals(appId)) {
                    log.warn("App id not match.");
                    return SUCCESS_RESP;
                }
//                weixinPushMsgDTO = XmlUtil.readValue(content, WeixinPushMsgDTO.class);
                weixinPushMsg = JsonUtil.readMapValue(content);

            }
            pushClientCallback(weixinPushMsg);
        }
        return SUCCESS_RESP;
    }

    private String pushClientCallback(Map<String, Object> weixinPushMsg) {
        String msgType = (String) weixinPushMsg.get("MsgType");
        String event = (String) weixinPushMsg.get("Event");
        log.info("Callback {}", weixinPushMsg);
        switch (msgType) {
        case "event": {
            switch (event) {
            case "wxa_media_check":
                contentCheckService.checkResultCallback(weixinPushMsg);
                break;
            default:
                log.info("Do nothing for event: {}", event);
            }
            break;
        }
        default:
            log.info("Do nothing for msgType : {}", msgType);
        }
        return SUCCESS_RESP;
    }

}
