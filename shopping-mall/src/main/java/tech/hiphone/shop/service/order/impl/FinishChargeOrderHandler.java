package tech.hiphone.shop.service.order.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import tech.hiphone.shop.constants.OrderStatus;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.repository.OrderInfoRepository;
import tech.hiphone.shop.service.coupon.CouponService;
import tech.hiphone.shop.service.event.TradeEvent;
import tech.hiphone.shop.service.goods.GoodsService;
import tech.hiphone.shop.service.order.OrderHandler;
import tech.hiphone.shop.service.order.dto.TradeDTO;

// 订单完成支付
@Service
public class FinishChargeOrderHandler implements OrderHandler, ApplicationListener<TradeEvent> {

    private static final Logger log = LoggerFactory.getLogger(FinishChargeOrderHandler.class);

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CouponService couponService;

    @Override
    public Object operate(OrderInfo orderInfo, Object reqValue) {
        if (orderInfo.getStatus() != OrderStatus.WAITING_CHARGE) {
            log.info("Order is not waiting pay");
            return orderInfo;
        }

        goodsService.finishCharge(orderInfo);
        couponService.useCouponSuccee(orderInfo);
        orderInfo.setStatus(OrderStatus.FINISH);
        orderInfoRepository.save(orderInfo);
        return orderInfo;
    }

    @Override
    public void onApplicationEvent(TradeEvent event) {
        TradeDTO tradeInfo = event.getSource();
        OrderInfo orderInfo = orderInfoRepository.findById(tradeInfo.getOrderId()).orElse(null);
        if (orderInfo == null) {
            log.warn("Cannot find order");
            return;
        } else {
            if (Optional.ofNullable(orderInfo.getPrice()).map(price -> !price.equals(tradeInfo.getPrice()))
                    .orElse(true)) {
                log.warn("Price not match");
                return;
            }
        }

        this.operate(orderInfo, null);
    }

}
