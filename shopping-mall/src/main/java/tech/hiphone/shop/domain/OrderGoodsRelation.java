package tech.hiphone.shop.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import tech.hiphone.shop.domain.id.OrderGoodsRelationId;

@Entity
@Table(name = "order_goods_relation")
public class OrderGoodsRelation {

    @EmbeddedId
    private OrderGoodsRelationId id;

    public OrderGoodsRelationId getId() {
        return id;
    }

    public void setId(OrderGoodsRelationId id) {
        this.id = id;
    }

}
