package tech.hiphone.weixin.service.dto.req;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WxaCodeReqDTO {

    @Size(max = 32)
    @JsonProperty("scene")
    private String scene;
    @JsonProperty("page")
    private String page;
    @JsonProperty("width")
    private Integer width;
    @JsonProperty("auto_color")
    private Boolean autoColor;
    @JsonProperty("line_color")
    private Color lineColor;

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Boolean getAutoColor() {
        return autoColor;
    }

    public void setAutoColor(Boolean autoColor) {
        this.autoColor = autoColor;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    class Color {
        private String r;
        private String g;
        private String b;

        public String getR() {
            return r;
        }

        public void setR(String r) {
            this.r = r;
        }

        public String getG() {
            return g;
        }

        public void setG(String g) {
            this.g = g;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

    }

}
