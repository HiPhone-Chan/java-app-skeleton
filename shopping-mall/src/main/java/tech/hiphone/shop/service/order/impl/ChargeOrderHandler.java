package tech.hiphone.shop.service.order.impl;

import java.time.Instant;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.shop.constants.OrderChannel;
import tech.hiphone.shop.constants.OrderStatus;
import tech.hiphone.shop.domain.Coupon;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.domain.common.Price;
import tech.hiphone.shop.repository.CouponRepository;
import tech.hiphone.shop.repository.OrderInfoRepository;
import tech.hiphone.shop.service.coupon.CouponService;
import tech.hiphone.shop.service.event.TradeEvent;
import tech.hiphone.shop.service.goods.GoodsService;
import tech.hiphone.shop.service.order.OrderHandler;
import tech.hiphone.shop.service.order.dto.ChargeInfoDTO;
import tech.hiphone.shop.service.order.dto.OrderInfoDTO;
import tech.hiphone.shop.service.order.dto.TradeDTO;

// 订单支付
@Service
public class ChargeOrderHandler implements OrderHandler {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ApplicationEventPublisher tradeEventPublisher;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CouponService couponService;

//	@Autowired
//	private WeixinPayService weixinPayService;
//
//	@Autowired
//	private AlipayService alipayService;

    @Override
    public Object operate(OrderInfo orderInfo, Object reqValue) {
        int orderStatus = orderInfo.getStatus();
        if (orderStatus != OrderStatus.CREATED) {
            throw new ServiceException(ErrorCodeContants.ORDER_STATUS_ERROR, "You cannot charge for this status.");
        }
        ChargeInfoDTO chargeInfoDTO = (ChargeInfoDTO) reqValue;
        return this.charge(orderInfo, chargeInfoDTO);
    }

    public Object charge(OrderInfo orderInfo, ChargeInfoDTO chargeInfoDTO) {
        goodsService.checkSituation(new OrderInfoDTO(orderInfo));
        chargeInfoDTO.setTitle(orderInfo.getTitle());
        chargeInfoDTO.setDescription(orderInfo.getDescription());

        String couponId = chargeInfoDTO.getCouponId();
        if (StringUtils.isNotEmpty(couponId)) {
            Coupon coupon = couponRepository.findById(Long.valueOf(couponId))
                    .orElseThrow(() -> new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot find coupon"));
            couponService.useCoupon(coupon, orderInfo);
        }

        Price price = orderInfo.getPrice();
        chargeInfoDTO.setAmount(price.getAmount());
        chargeInfoDTO.setCurrency(price.getCurrency());

        String channel = chargeInfoDTO.getChannel();
        Object result = null;
        switch (channel) {
        case OrderChannel.wx:
        case OrderChannel.wx_lite:
        case OrderChannel.wx_wap:
//			result = weixinPayService.charge(chargeInfoDTO);
            break;
        case OrderChannel.alipay:
//			result = alipayService.charge(chargeInfoDTO);
            break;
        case OrderChannel.sys_application:
//			sysApplicationPayService.charge(chargeInfoDTO);
        case OrderChannel.off_line:
            TradeDTO tradeDTO = new TradeDTO();
            tradeDTO.setOrderId(orderInfo.getId());
            tradeDTO.setPrice(price);
            orderInfo.setOriginalAmount(price.getAmount());
            result = tradeDTO;
            break;
        }
        if (result != null) {
            orderInfo.setChannel(channel);
            orderInfo.setStatus(OrderStatus.WAITING_CHARGE);
            orderInfo.setExpirationDate(Instant.now().plusSeconds(OrderInfo.DEFAULT_EXPIRE_TIME));
            orderInfoRepository.save(orderInfo);
        }

        if (OrderChannel.off_line.equals(channel) || OrderChannel.sys_application.equals(channel)) {
            tradeEventPublisher.publishEvent(new TradeEvent((TradeDTO) result));
        }
        return result;
    }

}
