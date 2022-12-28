package tech.hiphone.weixin.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tech.hiphone.cms.domain.common.MaterialInfo;
import tech.hiphone.weixin.domain.id.MediaCheckId;

// 微信媒体检测记录
@Entity
@Table(name = "wx_media_check_record", indexes = { @Index(columnList = "url") })
public class WxMediaCheckRecord {

    public static final int NO_RISK = 0;
    public static final int RISK = 1;

    @EmbeddedId
    private MediaCheckId id;

    @Column(name = "url", length = 511)
    private String url;

    @Column(name = "is_risky")
    private Integer isRisky;

    @Column(name = "status_code")
    private Integer statusCode;

    @Embedded
    private MaterialInfo materialInfo;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 63)
    @JsonIgnore
    private String lastModifiedBy;

    public MediaCheckId getId() {
        return id;
    }

    public void setId(MediaCheckId id) {
        this.id = id;
    }

    public MaterialInfo getMaterialInfo() {
        return materialInfo;
    }

    public void setMaterialInfo(MaterialInfo materialInfo) {
        this.materialInfo = materialInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

}
