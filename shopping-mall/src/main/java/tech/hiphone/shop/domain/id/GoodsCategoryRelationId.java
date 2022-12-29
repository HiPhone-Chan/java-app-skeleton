package tech.hiphone.shop.domain.id;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import tech.hiphone.shop.domain.Goods;
import tech.hiphone.shop.domain.GoodsCategory;

public class GoodsCategoryRelationId implements Serializable {

    private static final long serialVersionUID = 1;

    @ManyToOne
    @JoinColumn(name = "goods_id", nullable = false)
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private GoodsCategory category;

    public GoodsCategoryRelationId() {
    }

    public GoodsCategoryRelationId(Goods goods, GoodsCategory category) {
        super();
        this.goods = goods;
        this.category = category;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsCategory getCategory() {
        return category;
    }

    public void setCategory(GoodsCategory category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((goods == null) ? 0 : goods.hashCode());
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
        GoodsCategoryRelationId other = (GoodsCategoryRelationId) obj;
        if (category == null) {
            if (other.category != null)
                return false;
        } else if (!category.equals(other.category))
            return false;
        if (goods == null) {
            if (other.goods != null)
                return false;
        } else if (!goods.equals(other.goods))
            return false;
        return true;
    }

}
