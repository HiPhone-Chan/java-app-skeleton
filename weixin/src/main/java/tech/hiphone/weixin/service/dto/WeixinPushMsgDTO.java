package tech.hiphone.weixin.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeixinPushMsgDTO {

    @JsonProperty("ToUserName")
    private String toUserName;
    @JsonProperty("FromUserName")
    private String fromUserName;
    @JsonProperty("MsgType")
    private String msgType;
    @JsonProperty("Content")
    private String content;
    @JsonProperty("MsgId")
    private String msgId;
    @JsonProperty("Event")
    private String event;
    @JsonProperty("EventKey")
    private String eventKey;
    @JsonProperty("Ticket")
    private String ticket;
    @JsonProperty("Encrypt")
    private String encrypt;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    @Override
    public String toString() {
        return "WeixinPushMsgDTO [toUserName=" + toUserName + ", fromUserName=" + fromUserName + ", msgType=" + msgType
                + ", content=" + content + ", msgId=" + msgId + ", event=" + event + ", eventKey=" + eventKey
                + ", ticket=" + ticket + ", encrypt=" + encrypt + "]";
    }

}
