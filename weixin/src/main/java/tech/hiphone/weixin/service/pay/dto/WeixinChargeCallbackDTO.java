package tech.hiphone.weixin.service.pay.dto;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;

import tech.hiphone.weixin.domain.WxPayChargeResult;

public class WeixinChargeCallbackDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("create_time")
    private String createTime;

    // TRANSACTION.SUCCESS
    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("resource_type")
    private String resourceType;

    @JsonProperty("resource")
    private WeixinPayEncryptedMsg resource;

    @JsonProperty("summary")
    private String summary;

    public WeixinChargeCallbackDTO() {
    }

    public WxPayChargeResult toWxPayChargeResult() {
        WxPayChargeResult wxPayChargeResult = new WxPayChargeResult();
        wxPayChargeResult.setId(id);
        wxPayChargeResult.setCreateTime(DateTimeFormatter.ISO_ZONED_DATE_TIME.parse(createTime, Instant::from));
        wxPayChargeResult.setEventType(eventType);
        wxPayChargeResult.setResourceType(resourceType);
        wxPayChargeResult.setSummary(summary);
        return wxPayChargeResult;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public WeixinPayEncryptedMsg getResource() {
        return resource;
    }

    public void setResource(WeixinPayEncryptedMsg resource) {
        this.resource = resource;
    }

}
