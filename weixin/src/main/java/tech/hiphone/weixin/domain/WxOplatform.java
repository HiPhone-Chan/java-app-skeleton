package tech.hiphone.weixin.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

// 微信开放平台
@Entity
@Table(name = "wx_oplatform")
public class WxOplatform extends AbstractWxPlatform {

    private static final long serialVersionUID = 1L;

    public WxOplatform() {
    }

    public WxOplatform(AbstractWxPlatform abstractWxPlatform) {
        super(abstractWxPlatform);
    }

    @Override
    public WxOplatform clone() {
        return (WxOplatform) super.clone();
    }

}
