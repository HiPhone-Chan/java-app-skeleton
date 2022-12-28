package tech.hiphone.weixin.web.vm;

import java.time.Instant;
import java.util.Optional;

import tech.hiphone.commons.utils.JsonUtil;
import tech.hiphone.weixin.domain.WxPayChargeResult;

public class WxPayChargeResultVM {

    private String id;

    private long createTime;

    private Object resource;

    public WxPayChargeResultVM() {
    }

    public WxPayChargeResultVM(WxPayChargeResult wxPayChargeResult) {
        this.id = String.valueOf(wxPayChargeResult.getId());
        this.resource = JsonUtil.readValue(wxPayChargeResult.getResource());
        this.createTime = Optional.ofNullable(wxPayChargeResult.getCreateTime()).map(Instant::toEpochMilli).orElse(0l);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Object getResource() {
        return resource;
    }

    public void setResource(Object resource) {
        this.resource = resource;
    }

}
