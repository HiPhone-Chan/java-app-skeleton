package tech.hiphone.commons.web.vm;

import tech.hiphone.commons.domain.common.AbstractDescriptionEntity;

public abstract class AbstractDescriptionVM {

    private String name;

    private String description;

    private String imageUrl;

    public AbstractDescriptionVM() {
    }

    public AbstractDescriptionVM(AbstractDescriptionEntity abstractDescriptionEntity) {
        super();
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

    protected void set(AbstractDescriptionEntity abstractDescriptionEntity) {
        this.name = abstractDescriptionEntity.getName();
        this.description = abstractDescriptionEntity.getDescription();
        this.imageUrl = abstractDescriptionEntity.getImageUrl();
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
