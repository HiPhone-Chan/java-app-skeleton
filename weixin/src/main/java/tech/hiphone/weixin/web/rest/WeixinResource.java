package tech.hiphone.weixin.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.hiphone.weixin.service.WeixinService;

@RestController
@RequestMapping("/api/weixin")
public class WeixinResource {

    private static final Logger log = LoggerFactory.getLogger(WeixinResource.class);

    @Autowired
    private WeixinService weixinService;

    @GetMapping("/jsapi/ticket")
    public Object getJsApiTicket(@RequestParam("type") String type, @RequestParam("url") String url) {
        log.debug("Request to getJsApiTicket", url);
        return weixinService.getJsapiTicket(url, type);
    }

}
