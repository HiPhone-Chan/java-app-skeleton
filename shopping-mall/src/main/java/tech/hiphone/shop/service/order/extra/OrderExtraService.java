package tech.hiphone.shop.service.order.extra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.hiphone.commons.service.SpringService;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.service.order.dto.OrderExtraDTO;
import tech.hiphone.shop.service.order.extra.impl.NoopOrderExtraHandler;

@Service
public class OrderExtraService {

    private static final Logger log = LoggerFactory.getLogger(OrderExtraService.class);

    @Autowired
    private SpringService springService;

    private OrderExtraHandler defaultOrderExtraHandler = new NoopOrderExtraHandler();

    // 保存信息到其他表
    public void save(OrderInfo orderInfo, OrderExtraDTO orderExtraDTO) {
        if (orderExtraDTO == null) {
            return;
        }

        Object extraData = orderExtraDTO.getData();
        if (extraData == null) {
            return;
        }

        String type = orderExtraDTO.getType();
        OrderExtraHandler orderExtraHandler = getOrderExtraHandler(type);
        orderExtraHandler.save(orderInfo, orderExtraDTO);
    }

    private OrderExtraHandler getOrderExtraHandler(String type) {
        OrderExtraHandler orderExtraHandler = (OrderExtraHandler) springService
                .getBean(type + OrderExtraHandler.HANDLER_SUFFIX);

        if (orderExtraHandler == null) {
            log.debug("Use default OrderExtraHandler");
            return defaultOrderExtraHandler;
        }
        return orderExtraHandler;
    }

}
