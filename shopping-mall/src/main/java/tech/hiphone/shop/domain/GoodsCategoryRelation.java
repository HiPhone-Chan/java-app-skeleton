package tech.hiphone.shop.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import tech.hiphone.commons.domain.common.AbstractDescriptionEntity;
import tech.hiphone.shop.domain.id.GoodsCategoryRelationId;

// 商品分组关系
@Entity
@Table(name = "goods_category_relation")
public class GoodsCategoryRelation extends AbstractDescriptionEntity {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private GoodsCategoryRelationId id;

    public GoodsCategoryRelationId getId() {
        return id;
    }

    public void setId(GoodsCategoryRelationId id) {
        this.id = id;
    }

}
