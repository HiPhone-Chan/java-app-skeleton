package tech.hiphone.weixin.web.vm;

public class QrcodeVM {

    private String type;

    private Object info;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "QrcodeVM [type=" + type + ", info=" + info + "]";
    }

}
