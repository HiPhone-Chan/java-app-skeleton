package tech.hiphone.cms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import tech.hiphone.commons.domain.common.AbstractAuditingEntity;

// 素材 - 存储到本地用
@Entity
@Table(name = "cms_material", indexes = { @Index(name = "materialPath", columnList = "path") })
public class Material extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    // 唯一的相对路径
    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "file_ext", length = 15)
    private String fileExt;

    @Column(name = "is_public")
    private boolean isPublic = true;

    @Column(name = "name", length = 127)
    private String name;
    // MaterialType
    @Column(name = "type", length = 15)
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
