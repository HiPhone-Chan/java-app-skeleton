package tech.hiphone.weixin.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

// 微信支付结果
@Entity
@Table(name = "wx_pay_charge_result")
public class WxPayChargeResult {

    // 通知的唯一ID
    @Id
    private String id;

    // 通知创建的时间 为yyyy-MM-DDTHH:mm:ss+TIMEZONE
    @Column(name = "create_time")
    private Instant createTime;

    // TRANSACTION.SUCCESS
    @Column(name = "event_type", length = 20)
    private String eventType;

    @Column(name = "resource_type", length = 30)
    private String resourceType;

    @Column(name = "resource", length = 2047)
    private String resource;

    @Column(name = "summary", length = 63)
    private String summary;

    // 接收到微信通知的时间
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate = Instant.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate = Instant.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
