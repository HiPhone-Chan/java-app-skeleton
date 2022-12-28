package tech.hiphone.weixin.event;

public class WxMsgEvent extends WxEvent {

    private static final long serialVersionUID = -5689640823487358798L;

    private String authority;

    public WxMsgEvent(Object source) {
        super(source);
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
