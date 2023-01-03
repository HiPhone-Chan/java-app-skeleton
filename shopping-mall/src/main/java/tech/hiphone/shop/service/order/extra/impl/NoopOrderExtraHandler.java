package tech.hiphone.shop.service.order.extra.impl;

import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.service.order.dto.OrderExtraDTO;
import tech.hiphone.shop.service.order.extra.OrderExtraHandler;

public class NoopOrderExtraHandler implements OrderExtraHandler {

    @Override
    public Object save(OrderInfo orderInfo, OrderExtraDTO orderExtraDTO) {
        // DO nothing
        return null;
    }

}
