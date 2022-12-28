package tech.hiphone.weixin.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import tech.hiphone.commons.domain.User;
import tech.hiphone.commons.domain.common.AbstractDescriptionEntity;
import tech.hiphone.weixin.domain.id.WxUserId;

@Entity
@Table(name = "wx_user")
public class WxUser extends AbstractDescriptionEntity {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private WxUserId id;

    @Column(name = "union_id", length = 63)
    private String unionId;

    @Column(name = "session_key", length = 63)
    private String sessionKey;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public WxUserId getId() {
        return id;
    }

    public void setId(WxUserId id) {
        this.id = id;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
