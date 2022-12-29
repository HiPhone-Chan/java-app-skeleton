package tech.hiphone.shop.service.order.impl;

import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.service.order.OrderHandler;

public abstract class AbstractOrderHandler implements OrderHandler {

    public Object operate(OrderInfo orderInfo, Object reqValue) {
        // do nothing
        return null;
    }

}
