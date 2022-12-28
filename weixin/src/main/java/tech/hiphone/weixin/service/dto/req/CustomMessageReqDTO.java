package tech.hiphone.weixin.service.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomMessageReqDTO {

    @JsonProperty("touser")
    private String toUser;

    @JsonProperty("msgtype")
    private String msgType = "text";

    @JsonProperty("text")
    private Text text = new Text();

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text.setContent(text);
    }

    class Text {

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }

}
