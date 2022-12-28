package tech.hiphone.weixin.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import tech.hiphone.commons.domain.common.AbstractDescriptionEntity;

// 配置
@MappedSuperclass
public class AbstractWxPlatform extends AbstractDescriptionEntity implements Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "app_id", length = 31)
    private String appId;

    @Column(name = "app_secret", length = 63)
    private String appSecret;

    public AbstractWxPlatform() {
    }

    public AbstractWxPlatform(AbstractWxPlatform abstractWxPlatform) {
        this.appId = abstractWxPlatform.getAppId();
        this.appSecret = abstractWxPlatform.getAppSecret();
        this.setName(getName());
        this.setDescription(getDescription());
        this.setImageUrl(getImageUrl());
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Override
    public AbstractWxPlatform clone() {
        try {
            AbstractWxPlatform abstractWxPlatform = (AbstractWxPlatform) super.clone();
            abstractWxPlatform.setImageUrl(getImageUrl());
            abstractWxPlatform.setName(getName());
            abstractWxPlatform.setDescription(getDescription());
            return abstractWxPlatform;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new AbstractWxPlatform(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appId == null) ? 0 : appId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractWxPlatform other = (AbstractWxPlatform) obj;
        if (appId == null) {
            if (other.appId != null)
                return false;
        } else if (!appId.equals(other.appId))
            return false;
        return true;
    }

}
