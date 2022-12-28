package tech.hiphone.weixin.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

// 小程序
@Entity
@Table(name = "wx_mini_program")
public class WxMiniProgram extends WxOffiaccount {

    private static final long serialVersionUID = 1L;

    public WxMiniProgram() {
    }

    public WxMiniProgram(AbstractWxPlatform abstractWxPlatform) {
        super(abstractWxPlatform);
    }

    @Override
    public WxMiniProgram clone() {
        return (WxMiniProgram) super.clone();
    }

}
