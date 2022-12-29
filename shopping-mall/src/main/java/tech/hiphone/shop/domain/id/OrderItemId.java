package tech.hiphone.shop.domain.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import tech.hiphone.shop.domain.OrderInfo;

public class OrderItemId implements Serializable {

    private static final long serialVersionUID = 1;

    @ManyToOne
    @JoinColumn(name = "order_info_id", nullable = false)
    private OrderInfo orderInfo;

    @Column(name = "id", nullable = false)
    private Integer id;

    public OrderItemId() {
    }

    public OrderItemId(OrderInfo orderInfo, Integer id) {
        this.orderInfo = orderInfo;
        this.id = id;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        OrderItemId other = (OrderItemId) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (orderInfo == null) {
            if (other.orderInfo != null)
                return false;
        } else if (!orderInfo.equals(other.orderInfo))
            return false;
        return true;
    }

}
