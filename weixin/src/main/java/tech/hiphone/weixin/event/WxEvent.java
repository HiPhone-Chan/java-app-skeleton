package tech.hiphone.weixin.event;

import org.springframework.context.ApplicationEvent;

public class WxEvent extends ApplicationEvent {

    private static final long serialVersionUID = -3254625821755574280L;

    private String type;

    public WxEvent(Object source) {
        super(source);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
