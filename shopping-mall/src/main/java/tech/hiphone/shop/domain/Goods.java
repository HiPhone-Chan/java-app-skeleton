package tech.hiphone.shop.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import tech.hiphone.commons.domain.common.AbstractDescriptionEntity;
import tech.hiphone.shop.domain.common.Discount;
import tech.hiphone.shop.domain.common.Price;

// 商品
@Entity
@Table(name = "goods")
@Inheritance(strategy = InheritanceType.JOINED)
public class Goods extends AbstractDescriptionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", length = 15, nullable = false)
    private String type;

    @Column(name = "is_visible")
    private Boolean visible = false;

    @Column(name = "priority")
    private Integer priority;

    @Embedded
    private Price price;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "code", column = @Column(name = "discount_code")),
            @AttributeOverride(name = "amount", column = @Column(name = "discount_amount")) })
    private Discount discount;

    public Goods() {
    }

    public Goods(String type) {
        super();
        this.type = type;
    }

    public void set(Goods goods) {
        super.set(goods);
        this.id = goods.id;
        this.type = goods.type;
        this.visible = goods.visible;
        this.priority = goods.priority;
        this.price = goods.price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Goods other = (Goods) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
