package tech.hiphone.weixin.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import tech.hiphone.cms.constants.MaterialType;
import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.commons.utils.BeanUtil;
import tech.hiphone.commons.utils.MessageDigestUtils;
import tech.hiphone.weixin.config.WeixinProperties;
import tech.hiphone.weixin.config.WeixinProperties.WeixinBaseInfo;
import tech.hiphone.weixin.constants.MsgType;
import tech.hiphone.weixin.constants.WeixinConstants;
import tech.hiphone.weixin.event.WxEvent;
import tech.hiphone.weixin.event.WxMsgEvent;
import tech.hiphone.weixin.security.authentication.WeixinAuthenticationToken;
import tech.hiphone.weixin.service.dto.TicketInfoDTO;
import tech.hiphone.weixin.service.dto.TokenInfoDTO;
import tech.hiphone.weixin.service.dto.req.CustomMessageReqDTO;
import tech.hiphone.weixin.service.dto.req.WxaCodeReqDTO;
import tech.hiphone.weixin.service.dto.resp.AccessTokenRespDTO;
import tech.hiphone.weixin.service.dto.resp.JsApiTicketRespDTO;
import tech.hiphone.weixin.service.dto.resp.ReplyMessageDTO;
import tech.hiphone.weixin.service.handler.WeixinApiHandler;

@Service
@Transactional
public class WeixinService implements ApplicationListener<WxEvent> {

    private static final Logger log = LoggerFactory.getLogger(WeixinService.class);

    public final static long TMP_SCENE_VALID_PERIO = 3 * 60;
    public final static int SUCCESS = 0;

    // appId: TokenInfoDTO
    private Map<String, TokenInfoDTO> tokenCache = new HashMap<>();
    // appId: TokenInfoDTO
    private Map<String, TokenInfoDTO> ticketCache = new HashMap<>();

    private WeixinApiHandler weixinApiHandler;

    @Autowired
    private WeixinProperties weixinProperties;

    public WeixinService(RestTemplate restTemplate) {
        weixinApiHandler = new WeixinApiHandler(restTemplate);
    }

    public Map<String, Object> getInfoFromCode(WeixinAuthenticationToken authentication) {
        String appId = (String) authentication.getPrincipal();
        String code = (String) authentication.getCredentials();
        WeixinBaseInfo weixinBaseInfo = weixinProperties.getOffiaccount(appId);

        Map<String, Object> result = null;
        String type = weixinBaseInfo.getType();
        switch (type) {
        case WeixinConstants.TYPE_MINIPROGRAM:
            result = weixinApiHandler.getSessionKey(code, weixinBaseInfo);
            break;
        case WeixinConstants.TYPE_OFFIACCOUNT:
        case WeixinConstants.TYPE_OPLATFORM:
            result = weixinApiHandler.getAccessToken(code, weixinBaseInfo);
            String openId = (String) result.get("openid");
            if (StringUtils.isEmpty(openId)) {
                log.warn("Cannot get openId from code");
                throw new BadCredentialsException("Cannot get openId from code");
            }
            String accessToken = (String) result.get("access_token");
            if (StringUtils.isEmpty(accessToken)) {
                log.warn("Cannot get accessToken from code");
                throw new BadCredentialsException("Cannot get accessToken from code");
            }
            Map<String, Object> newResult = weixinApiHandler.getSnsUserInfo(accessToken, openId);
            result.putAll(newResult);
            break;
        }

        if (result == null) {
            log.warn("Cannot get result from code");
            throw new BadCredentialsException("Cannot get result from code");
        }

        return result;
    }

    // 安全检查
    public boolean imgSecCheck(String appId, File file) {
        Map<String, Object> result = weixinApiHandler.imgSecCheck(getAccessToken(appId), file);
        Integer errCode = (Integer) result.get("errcode");
        if (SUCCESS == errCode) {
            return true;
        } else if (87014 == errCode) {
            // 内容可能潜在风险
            return false;
        }
        throw new ServiceException(ErrorCodeContants.THIRD_PRTY_ERROR, (String) result.get("errmsg"));
    }

    public String mediaSecCheck(String appId, String url, String materialType) {
        // 1:音频;2:图片
        int mediaType = 1;
        if (MaterialType.VIDEO.equals(materialType)) {
            mediaType = 2;
        }

        Map<String, Object> result = weixinApiHandler.mediaSecCheck(getAccessToken(appId), url, mediaType);
        Integer errCode = (Integer) result.get("errcode");
        if (SUCCESS == errCode) {
            return (String) result.get("trace_id");
        } else {
            log.warn("MediaSecCheck fail", result);
        }
        throw new ServiceException(ErrorCodeContants.THIRD_PRTY_ERROR, (String) result.get("errmsg"));
    }

    public boolean msgSecCheck(String appId, String content) {
        Map<String, Object> result = weixinApiHandler.msgSecCheck(getAccessToken(appId), content);
        Integer errCode = (Integer) result.get("errcode");
        if (SUCCESS == errCode) {
            return true;
        } else if (87014 == errCode) {
            // 内容可能潜在风险
            return false;
        }
        throw new ServiceException(ErrorCodeContants.THIRD_PRTY_ERROR, (String) result.get("errmsg"));
    }

    public byte[] getWxaCode(String scene, String appId) {
        WxaCodeReqDTO wxaCodeReqDTO = new WxaCodeReqDTO();
        wxaCodeReqDTO.setScene(scene);
        return weixinApiHandler.generateWxaCode(getAccessToken(appId), wxaCodeReqDTO);
    }

    public byte[] getQrcode(String ticket) {
        return weixinApiHandler.generateQrcode(ticket);
    }

    public String getAccessToken(String appId) {
        TokenInfoDTO tokenInfoDTO = tokenCache.get(appId);

        if (tokenInfoDTO == null) {
            synchronized (ticketCache) {
                tokenInfoDTO = tokenCache.get(appId);
                if (tokenInfoDTO == null) {
                    tokenInfoDTO = new TokenInfoDTO();
                    tokenCache.put(appId, tokenInfoDTO);
                }
            }
        }

        if (tokenInfoDTO.needsRefresh()) {
            synchronized (tokenInfoDTO) {
                if (tokenInfoDTO.needsRefresh()) {
                    WeixinBaseInfo weixinBaseInfo = weixinProperties.getOffiaccount(appId);
                    AccessTokenRespDTO result = weixinApiHandler.getBaseAccessToken(weixinBaseInfo);
                    if (result != null) {
                        String accessToken = result.getAccessToken();
                        if (StringUtils.isNotEmpty(accessToken)) {
                            long expireTime = System.currentTimeMillis() + (result.getExpiresIn() - 10 * 60) * 1000;
                            tokenInfoDTO.setAccessToken(accessToken);
                            tokenInfoDTO.setExpireTime(expireTime);
                            tokenCache.put(appId, tokenInfoDTO);
                        }
                    }
                }
            }
        }

        return tokenInfoDTO.getAccessToken();
    }

    public TicketInfoDTO getJsapiTicket(String url, String appId) {
        TokenInfoDTO ticketInfo = getTicketInfo(appId);
        long timestamp = System.currentTimeMillis() / 1000;
        TicketInfoDTO jsapiTicketInfo = new TicketInfoDTO();
        jsapiTicketInfo.setJsapiTicket(ticketInfo.getAccessToken());
        jsapiTicketInfo.setTimestamp(timestamp);
        try {
            jsapiTicketInfo.setUrl(URLDecoder.decode(url, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            jsapiTicketInfo.setUrl(url);
            e.printStackTrace();
        }
        jsapiTicketInfo.setNonceStr(WeixinApiHandler.generateNonceStr());

        String sortString = BeanUtil.sortString(jsapiTicketInfo);
        log.debug("sortString {}", sortString);
        String sign = MessageDigestUtils.sha1Encode(sortString);
        jsapiTicketInfo.setSignature(sign);
        log.debug("ticket {}", jsapiTicketInfo);
        return jsapiTicketInfo;
    }

    public TokenInfoDTO getTicketInfo(String appId) {
        TokenInfoDTO ticketInfo = ticketCache.get(appId);

        if (ticketInfo == null) {
            synchronized (ticketCache) {
                ticketInfo = ticketCache.get(appId);
                if (ticketInfo == null) {
                    ticketInfo = new TokenInfoDTO();
                    ticketCache.put(appId, ticketInfo);
                }
            }
        }

        if (ticketInfo.needsRefresh()) {
            synchronized (ticketInfo) {
                if (ticketInfo.needsRefresh()) {
                    JsApiTicketRespDTO result = weixinApiHandler.getJsApiTicket(getAccessToken(appId));
                    if (result != null) {
                        String ticket = result.getTicket();
                        if (StringUtils.isNotEmpty(ticket)) {
                            long expireTime = System.currentTimeMillis() + (result.getExpiresIn() - 10 * 60) * 1000;
                            ticketInfo.setAccessToken(ticket);
                            ticketInfo.setExpireTime(expireTime);
                        }
                    }
                }
            }
        }
        return ticketInfo.clone();
    }

    public void sendCustomMessage(String appId, String toUser, String message) {
        CustomMessageReqDTO customMessageReqDTO = new CustomMessageReqDTO();
        customMessageReqDTO.setToUser(toUser);
        customMessageReqDTO.setText(message);
        weixinApiHandler.sendCustomMessage(getAccessToken(appId), customMessageReqDTO);
    }

    public ReplyMessageDTO replyTextMessage(String fromUser, String toUser, String message) {
        ReplyMessageDTO replyMessage = new ReplyMessageDTO();
        replyMessage.setToUserName(toUser);
        replyMessage.setFromUserName(fromUser);
        replyMessage.setMsgType(MsgType.TEXT);
        replyMessage.setContent(message);
        return replyMessage;
    }

    @Override
    public void onApplicationEvent(WxEvent event) {
        log.debug("Get wx event");
        if (event instanceof WxMsgEvent) {
            String text = (String) event.getSource();
            log.debug("Get msg event : {}", text);

        }
    }

}
