package tech.hiphone.shop.service.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.commons.service.SpringService;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.repository.OrderInfoRepository;

@Service
@Transactional
public class OrderInfoService {

    private static final Logger log = LoggerFactory.getLogger(OrderInfoService.class);

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private SpringService springService;

    // 订单操作
    public Object operateOrder(String operator, String orderId, Object req) {
        OrderInfo orderInfo = orderInfoRepository.findById(orderId)
                .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot find order."));
        return this.operateOrder(operator, orderInfo, req);
    }

    public Object operateOrder(String operator, OrderInfo orderInfo) {
        return this.operateOrder(operator, orderInfo, null);
    }

    // 订单操作
    public Object operateOrder(String operator, OrderInfo orderInfo, Object req) {
        OrderHandler orderHandler = getOrderHandler(operator);
        Object result = orderHandler.operate(orderInfo, req);
        return result;
    }

    private OrderHandler getOrderHandler(String operator) {
        OrderHandler orderHandler = (OrderHandler) springService.getBean(operator + OrderHandler.HANDLER_SUFFIX);

        if (orderHandler == null) {
            log.error("get orderHandler fail: {}", operator);
            throw new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot get orderHandler.");
        }
        return orderHandler;
    }

}
