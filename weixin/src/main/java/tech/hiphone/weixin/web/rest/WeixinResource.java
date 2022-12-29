package tech.hiphone.weixin.web.rest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.hiphone.commons.utils.JsonUtil;
import tech.hiphone.weixin.constants.WxAuthoritiesConstants;
import tech.hiphone.weixin.service.WeixinService;
import tech.hiphone.weixin.web.vm.UserInfoVM;

@RestController
@RequestMapping("/api/weixin")
public class WeixinResource {

    private static final Logger log = LoggerFactory.getLogger(WeixinResource.class);

    private final WeixinService weixinService;

    public WeixinResource(WeixinService weixinService) {
        this.weixinService = weixinService;
    }

    @GetMapping("/api/weixin/user/info")
    @Secured({ WxAuthoritiesConstants.WEIXIN })
    public UserInfoVM getUserInfo() {
        Map<String, Object> result = weixinService.getUserInfo();
        log.debug("user info {}", result);
        return JsonUtil.convertValue(result, UserInfoVM.class);
    }

    @GetMapping("/jsapi/ticket")
    public Object getJsApiTicket(@RequestParam("appId") String appId, @RequestParam("url") String url) {
        log.debug("Request to getJsApiTicket", url);
        return weixinService.getJsapiTicket(url, appId);
    }

}
