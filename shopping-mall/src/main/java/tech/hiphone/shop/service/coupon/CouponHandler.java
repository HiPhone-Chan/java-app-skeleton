package tech.hiphone.shop.service.coupon;

import tech.hiphone.shop.domain.Coupon;
import tech.hiphone.shop.domain.OrderInfo;

// couponLimit
public interface CouponHandler {

    String HANDLER_SUFFIX = "CouponHandler";

    boolean check(Coupon coupon, OrderInfo orderInfo);
}
