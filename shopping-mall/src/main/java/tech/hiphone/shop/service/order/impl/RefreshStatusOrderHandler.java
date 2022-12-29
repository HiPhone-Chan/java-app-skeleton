package tech.hiphone.shop.service.order.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tech.hiphone.shop.constants.OrderChannel;
import tech.hiphone.shop.constants.OrderStatus;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.repository.OrderInfoRepository;
import tech.hiphone.shop.service.order.OrderHandler;

@Service
public class RefreshStatusOrderHandler implements OrderHandler {

	private static final Logger log = LoggerFactory.getLogger(RefreshStatusOrderHandler.class);

	@Autowired
	private OrderInfoRepository orderInfoRepository;

//	@Autowired
//	private WeixinPayService weixinPayService;
//
//	@Autowired
//	private AlipayService alipayService;

	@Override
	public Object operate(OrderInfo orderInfo, Object reqValue) {
		String channel = orderInfo.getChannel();
		switch (channel) {
		case OrderChannel.wx:
		case OrderChannel.wx_lite:
//			weixinPayService.refreshOrderInfo(orderInfo);
			break;
		case OrderChannel.alipay:
//			alipayService.refreshOrderInfo(orderInfo);
			break;
		default:
			log.warn("Channel not supported {}", channel);
		}
		return orderInfo;
	}

	// 刷新等待支付的订单状态
	@Scheduled(fixedDelay = 60 * 1000)
	public void refreshWaitingChargeOrderStatusTask() {
		Specification<OrderInfo> spec = (root, query, criteriaBuilder) -> {
			List<Predicate> andList = new ArrayList<>();

			andList.add(criteriaBuilder.equal(root.get("status"), OrderStatus.WAITING_CHARGE));
			query.where(criteriaBuilder.and(andList.toArray(new Predicate[andList.size()])));
			return query.getRestriction();
		};

		Pageable pageable = PageRequest.of(0, 100);
		Page<OrderInfo> page = Page.empty();
		do {
			page = orderInfoRepository.findAll(spec, pageable);
			for (OrderInfo orderInfo : page) {
				try {
					this.operate(orderInfo, null);
				} catch (Exception e) {
					log.warn("RefreshOrderStatus fail", e);
				}
			}
			pageable = page.nextPageable();
		} while (page.hasNext());

	}

	// 刷新创建的订单状态
	@Scheduled(fixedDelay = 60 * 1000)
	public void refreshCreatedOrderStatusTask() {
		Specification<OrderInfo> spec = (root, query, criteriaBuilder) -> {
			List<Predicate> andList = new ArrayList<>();
			andList.add(criteriaBuilder.equal(root.get("status"), OrderStatus.CREATED));
			andList.add(criteriaBuilder.lessThan(root.get("expirationDate"), Instant.now()));
			query.where(criteriaBuilder.and(andList.toArray(new Predicate[andList.size()])));
			return query.getRestriction();
		};

		Pageable pageable = PageRequest.of(0, 100);
		Page<OrderInfo> page = Page.empty();
		do {
			page = orderInfoRepository.findAll(spec, pageable);
			for (OrderInfo orderInfo : page) {
				orderInfo.setStatus(OrderStatus.EXPIRED);
			}
			try {
				orderInfoRepository.saveAll(page.getContent());
			} catch (Exception e) {
				log.warn("RefreshOrderStatus fail", e);
			}
			pageable = page.nextPageable();
		} while (page.hasNext());
	}

}
