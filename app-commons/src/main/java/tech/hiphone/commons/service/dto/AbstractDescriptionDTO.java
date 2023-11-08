package tech.hiphone.commons.service.dto;

import tech.hiphone.commons.domain.common.AbstractDescriptionEntity;

public abstract class AbstractDescriptionDTO extends AbstractAuditingDTO {

    private String name;

    private String description;

    private String imageUrl;

    public AbstractDescriptionDTO() {
    }

    public AbstractDescriptionDTO(AbstractDescriptionEntity abstractDescriptionEntity) {
        super(abstractDescriptionEntity);
        this.name = abstractDescriptionEntity.getName();
        this.description = abstractDescriptionEntity.getDescription();
        this.imageUrl = abstractDescriptionEntity.getImageUrl();
    }

    public AbstractDescriptionDTO(AbstractDescriptionDTO abstractDescriptionEntity) {
        this.set(abstractDescriptionEntity);
    }

    public AbstractDescriptionEntity toAbstractDescriptionEntity() {
        return new AbstractDescriptionEntity() {
            private static final long serialVersionUID = 1L;
            {
                setName(name);
                setDescription(description);
                setImageUrl(imageUrl);
            }
        };
    }

    public void set(AbstractDescriptionDTO entity) {
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
