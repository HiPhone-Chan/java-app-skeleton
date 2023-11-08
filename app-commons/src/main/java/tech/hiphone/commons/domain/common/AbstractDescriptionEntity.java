package tech.hiphone.commons.domain.common;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractDescriptionEntity extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "name", length = 63)
    private String name;

    @Column(name = "description", length = 1023)
    private String description;

    @Column(name = "image_url", length = 511)
    private String imageUrl;

    public AbstractDescriptionEntity() {
    }

    public AbstractDescriptionEntity(AbstractDescriptionEntity entity) {
        this.set(entity);
    }

    public void set(AbstractDescriptionEntity entity) {
        this.name = entity.name;
        this.description = entity.description;
        this.imageUrl = entity.imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
