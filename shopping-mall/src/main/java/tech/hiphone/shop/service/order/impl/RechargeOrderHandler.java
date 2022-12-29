package tech.hiphone.shop.service.order.impl;

import org.springframework.stereotype.Service;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.shop.constants.OrderStatus;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.service.order.dto.ChargeInfoDTO;

// 订单支付
@Service
public class RechargeOrderHandler extends ChargeOrderHandler {

	@Override
	public Object operate(OrderInfo orderInfo, Object reqValue) {
		int orderStatus = orderInfo.getStatus();
		if (orderStatus != OrderStatus.WAITING_CHARGE) {
			throw new ServiceException(ErrorCodeContants.ORDER_STATUS_ERROR, "You cannot recharge for this status.");
		}
		ChargeInfoDTO chargeInfoDTO = (ChargeInfoDTO) reqValue;
		return this.charge(orderInfo, chargeInfoDTO);
	}

}
