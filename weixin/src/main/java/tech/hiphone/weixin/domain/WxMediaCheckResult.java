package tech.hiphone.weixin.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tech.hiphone.weixin.domain.id.MediaCheckId;

// 微信媒体检测结果
@Entity
@Table(name = "wx_media_check_result")
public class WxMediaCheckResult {

    @EmbeddedId
    private MediaCheckId id;

    @Column(name = "is_risky")
    private Integer isRisky;

    @Column(name = "status_code")
    private Integer statusCode;

    @Column(name = "version")
    private Integer version;

    @Column(name = "result", length = 1024)
    private String result;

    @Column(name = "detail", length = 1024)
    private String detail;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();

    public MediaCheckId getId() {
        return id;
    }

    public void setId(MediaCheckId id) {
        this.id = id;
    }

    public Integer getIsRisky() {
        return isRisky;
    }

    public void setIsRisky(Integer isRisky) {
        this.isRisky = isRisky;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
