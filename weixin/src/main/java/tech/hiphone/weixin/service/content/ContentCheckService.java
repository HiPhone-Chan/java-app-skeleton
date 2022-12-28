package tech.hiphone.weixin.service.content;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.hiphone.cms.constants.ContentCheckStatus;
import tech.hiphone.commons.utils.JsonUtil;
import tech.hiphone.weixin.domain.WxMediaCheckResult;
import tech.hiphone.weixin.domain.id.MediaCheckId;
import tech.hiphone.weixin.repository.WxMediaCheckResultRepository;
import tech.hiphone.weixin.service.WeixinService;

@Service
public class ContentCheckService {

    @Autowired
    private WxMediaCheckResultRepository wxMediaCheckResultRepository;

    @Autowired
    private WeixinService weixinService;

    public static boolean isSafeStatus(int status) {
        return ContentCheckStatus.SAFE_LIST.contains(status);
    }

    public boolean msgSecCheck(String appId, String content) {
        if (StringUtils.isEmpty(content)) {
            return true;
        }
        return weixinService.msgSecCheck(appId, content);
    }

    // 微信回调
    public void checkResultCallback(Map<String, Object> weixinPushMsg) {
        String appId = (String) weixinPushMsg.get("appid");
        String traceId = (String) weixinPushMsg.get("trace_id");
        Integer version = (Integer) weixinPushMsg.get("version");
        Integer isRisky = (Integer) weixinPushMsg.get("isrisky");
        Integer statusCode = Integer.valueOf(weixinPushMsg.get("status_code").toString());

        MediaCheckId id = new MediaCheckId(appId, traceId);
        WxMediaCheckResult wxMediaCheckResult = wxMediaCheckResultRepository.findById(id).orElseGet(() -> {
            WxMediaCheckResult newWxMediaCheckResult = new WxMediaCheckResult();
            newWxMediaCheckResult.setId(id);
            return newWxMediaCheckResult;
        });
        wxMediaCheckResult.setIsRisky(isRisky);
        wxMediaCheckResult.setStatusCode(statusCode);
        wxMediaCheckResult.setVersion(version);
        wxMediaCheckResult.setResult(JsonUtil.toJsonString(weixinPushMsg.get("result")));
        wxMediaCheckResult.setDetail(JsonUtil.toJsonString(weixinPushMsg.get("detail")));
        wxMediaCheckResultRepository.save(wxMediaCheckResult);
    }

}
