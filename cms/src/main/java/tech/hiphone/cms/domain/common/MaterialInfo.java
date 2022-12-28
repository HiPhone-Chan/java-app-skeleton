package tech.hiphone.cms.domain.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MaterialInfo {

    // MaterialType
    @Column(name = "material_type", length = 15)
    private String type;

    // MaterialInfoType
    @Column(name = "material_info_type", length = 15)
    private String infoType;

    // 根据infoType提供不同的信息，可以多个
    // word -> 文字
    // path -> url
    // aliyunId -> 阿里云素材id，用于换取具体路径
    @Column(name = "material_info", length = 2047)
    private String info;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
