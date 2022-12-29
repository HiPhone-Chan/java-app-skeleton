package tech.hiphone.shop.service.order.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import tech.hiphone.shop.constants.OrderStatus;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.repository.OrderInfoRepository;
import tech.hiphone.shop.service.coupon.CouponService;
import tech.hiphone.shop.service.event.CloseEvent;
import tech.hiphone.shop.service.order.OrderHandler;

// 订单完成支付
@Service
public class CloseOrderHandler implements OrderHandler, ApplicationListener<CloseEvent> {

	private static final Logger log = LoggerFactory.getLogger(CloseOrderHandler.class);

	@Autowired
	private OrderInfoRepository orderInfoRepository;

	@Autowired
	private CouponService couponService;

	@Override
	public Object operate(OrderInfo orderInfo, Object reqValue) {
		if (orderInfo.getStatus() != OrderStatus.WAITING_CHARGE) {
			log.info("Order is not waiting pay");
			return orderInfo;
		}

		orderInfo.setStatus(OrderStatus.FAILED);
		orderInfoRepository.save(orderInfo);
		couponService.cancelUseCoupon(orderInfo);
		return orderInfo;
	}

	@Override
	public void onApplicationEvent(CloseEvent event) {
		String orderId = event.getSource();
		OrderInfo orderInfo = orderInfoRepository.findById(orderId).orElse(null);

		this.operate(orderInfo, null);
	}

}
