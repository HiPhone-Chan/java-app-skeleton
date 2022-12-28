package tech.hiphone.cms.web.vm;

import org.springframework.web.multipart.MultipartFile;

import tech.hiphone.cms.domain.Material;

public class MaterialVM {

    private String id;

    private MultipartFile[] file;

    private boolean isPublic = true;

    private String name;

    private String type;

    private String path;

    public Material toMaterial() {
        Material material = new Material();
        material.setId(this.path);
        material.setPublic(this.isPublic);
        material.setName(this.name);
        material.setType(this.type);
        return material;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MultipartFile[] getFile() {
        return file;
    }

    public void setFile(MultipartFile[] file) {
        this.file = file;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
