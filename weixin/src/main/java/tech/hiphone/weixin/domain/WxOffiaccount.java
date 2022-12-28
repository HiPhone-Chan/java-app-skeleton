package tech.hiphone.weixin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

// 公众平台
@Entity
@Table(name = "wx_offiaccount")
@Inheritance(strategy = InheritanceType.JOINED)
public class WxOffiaccount extends AbstractWxPlatform {

    private static final long serialVersionUID = 1L;

    @Column(name = "token", length = 31)
    private String token;

    @Column(name = "encoding_aes_key", length = 63)
    private String encodingAesKey;

    public WxOffiaccount() {
    }

    public WxOffiaccount(AbstractWxPlatform abstractWxPlatform) {
        super(abstractWxPlatform);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEncodingAesKey() {
        return encodingAesKey;
    }

    public void setEncodingAesKey(String encodingAesKey) {
        this.encodingAesKey = encodingAesKey;
    }

    @Override
    public WxOffiaccount clone() {
        return (WxOffiaccount) super.clone();
    }

}
