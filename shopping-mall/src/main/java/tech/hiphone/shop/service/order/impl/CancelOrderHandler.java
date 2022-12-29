package tech.hiphone.shop.service.order.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.hiphone.shop.constants.OrderStatus;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.repository.OrderInfoRepository;
import tech.hiphone.shop.service.coupon.CouponService;
import tech.hiphone.shop.service.order.OrderHandler;

@Service
public class CancelOrderHandler implements OrderHandler {

	@Autowired
	private OrderInfoRepository orderInfoRepository;

	@Autowired
	private CouponService couponService;

	@Override
	public Object operate(OrderInfo orderInfo, Object reqValue) {
		String reason = (String) reqValue;
		orderInfo.setReason(reason);
		orderInfo.setStatus(OrderStatus.CANCEL);
		orderInfoRepository.save(orderInfo);
		couponService.cancelUseCoupon(orderInfo);
		return orderInfo;
	}

}
