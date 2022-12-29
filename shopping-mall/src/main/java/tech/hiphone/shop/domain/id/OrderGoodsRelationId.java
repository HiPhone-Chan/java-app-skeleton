package tech.hiphone.shop.domain.id;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import tech.hiphone.shop.domain.Goods;
import tech.hiphone.shop.domain.OrderInfo;

public class OrderGoodsRelationId implements Serializable {

    private static final long serialVersionUID = 1;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderInfo orderInfo;

    @ManyToOne
    @JoinColumn(name = "goods_id", nullable = false)
    private Goods goods;

    public OrderGoodsRelationId() {
    }

    public OrderGoodsRelationId(OrderInfo orderInfo, Goods goods) {
        super();
        this.orderInfo = orderInfo;
        this.goods = goods;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((goods == null) ? 0 : goods.hashCode());
        result = prime * result + ((orderInfo == null) ? 0 : orderInfo.hashCode());
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
        OrderGoodsRelationId other = (OrderGoodsRelationId) obj;
        if (goods == null) {
            if (other.goods != null)
                return false;
        } else if (!goods.equals(other.goods))
            return false;
        if (orderInfo == null) {
            if (other.orderInfo != null)
                return false;
        } else if (!orderInfo.equals(other.orderInfo))
            return false;
        return true;
    }

}
