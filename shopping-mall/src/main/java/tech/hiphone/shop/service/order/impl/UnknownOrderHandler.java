package tech.hiphone.shop.service.order.impl;

import org.springframework.stereotype.Service;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.service.order.OrderHandler;

@Service
public class UnknownOrderHandler implements OrderHandler {

    @Override
    public Object operate(OrderInfo orderInfo, Object reqValue) {
        throw new ServiceException(ErrorCodeContants.OTHER_ERROR, "Not implement.");
    }

}
