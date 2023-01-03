package tech.hiphone.shop.service.order.extra;

import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.service.order.dto.OrderExtraDTO;

public interface OrderExtraHandler {

    String HANDLER_SUFFIX = "OrderExtraHandler";

    // 保存信息到其他表
    Object save(OrderInfo orderInfo, OrderExtraDTO orderExtraDTO);
}
