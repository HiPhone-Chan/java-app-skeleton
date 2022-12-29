package tech.hiphone.shop.service.order;

import tech.hiphone.shop.domain.OrderInfo;

public interface OrderHandler {

    String HANDLER_SUFFIX = "OrderHandler";

    Object operate(OrderInfo orderInfo, Object reqValue);
}
