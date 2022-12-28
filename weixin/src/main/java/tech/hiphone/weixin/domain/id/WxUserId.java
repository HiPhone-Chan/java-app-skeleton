package tech.hiphone.weixin.domain.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WxUserId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "app_id", length = 63)
    private String appId;

    @Column(name = "open_id", length = 63)
    private String openId;

    public WxUserId() {
    }

    public WxUserId(String appId, String openId) {
        super();
        this.appId = appId;
        this.openId = openId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appId == null) ? 0 : appId.hashCode());
        result = prime * result + ((openId == null) ? 0 : openId.hashCode());
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
        WxUserId other = (WxUserId) obj;
        if (appId == null) {
            if (other.appId != null)
                return false;
        } else if (!appId.equals(other.appId))
            return false;
        if (openId == null) {
            if (other.openId != null)
                return false;
        } else if (!openId.equals(other.openId))
            return false;
        return true;
    }

}
