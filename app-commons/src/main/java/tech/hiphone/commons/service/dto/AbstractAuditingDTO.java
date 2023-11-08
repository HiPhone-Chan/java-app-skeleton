package tech.hiphone.commons.service.dto;

import tech.hiphone.commons.domain.common.AbstractAuditingEntity;

public class AbstractAuditingDTO {

    private long createdDate;

    private long lastModifiedDate;

    public AbstractAuditingDTO() {
    }

    public AbstractAuditingDTO(AbstractAuditingEntity entity) {
        this.createdDate = entity.getCreatedDate().toEpochMilli();
        this.lastModifiedDate = entity.getLastModifiedDate().toEpochMilli();
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
