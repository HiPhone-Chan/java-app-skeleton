package tech.hiphone.shop.repository;

import java.util.List;

import tech.hiphone.framework.jpa.support.JpaExtRepository;
import tech.hiphone.shop.domain.Coupon;
import tech.hiphone.shop.domain.OrderInfo;

public interface CouponRepository extends JpaExtRepository<Coupon, Long> {

    List<Coupon> findByOrderInfo(OrderInfo orderInfo);

    Coupon findByOrderInfoId(String orderId);

}
