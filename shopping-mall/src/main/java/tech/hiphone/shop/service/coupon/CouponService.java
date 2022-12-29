package tech.hiphone.shop.service.coupon;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.domain.User;
import tech.hiphone.commons.exceptioin.ServiceException;
import tech.hiphone.commons.service.SpringService;
import tech.hiphone.shop.constants.CouponStatus;
import tech.hiphone.shop.constants.CouponType;
import tech.hiphone.shop.domain.Coupon;
import tech.hiphone.shop.domain.CouponTemplate;
import tech.hiphone.shop.domain.OrderInfo;
import tech.hiphone.shop.domain.common.Price;
import tech.hiphone.shop.repository.CouponRepository;

@Service
@Transactional
public class CouponService {

    private final String DEFAULT = "default";

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private SpringService springService;

    // 使用优惠券
    public void useCoupon(Coupon coupon, OrderInfo orderInfo) {
        // 检查
        String couponLimit = coupon.getTemplate().getCouponLimit();
        if (StringUtils.isEmpty(couponLimit)) {
            couponLimit = DEFAULT;
        }
        CouponHandler couponHandler = getCouponHandler(couponLimit);
        if (!couponHandler.check(coupon, orderInfo)) {
            throw new ServiceException(ErrorCodeContants.LACK_OF_DATA, "Cannot find coupon");
        }

        Price price = orderInfo.getPrice();
        price = CouponService.calc(price, coupon);
        orderInfo.setPrice(price);

        coupon.setStatus(CouponStatus.USING);
        coupon.setOrderInfo(orderInfo);
        couponRepository.save(coupon);
    }

    // 使用优惠券成功
    public void useCouponSuccee(OrderInfo orderInfo) {
        List<Coupon> couponList = couponRepository.findByOrderInfo(orderInfo);
        for (Coupon coupon : couponList) {
            coupon.setStatus(CouponStatus.USED);
        }
        couponRepository.saveAll(couponList);
    }

    public void cancelUseCoupon(OrderInfo orderInfo) {
        List<Coupon> couponList = couponRepository.findByOrderInfo(orderInfo);
        for (Coupon coupon : couponList) {
            coupon.setStatus(CouponStatus.RECEIVED);
            coupon.setOrderInfo(null);
        }
        couponRepository.saveAll(couponList);
    }

    public void updateCoupons(User user) {
        Instant endDate = toInstant(LocalDate.now().atStartOfDay());
        Specification<Coupon> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> andList = new ArrayList<>();

            andList.add(criteriaBuilder.equal(root.get("user"), user));
            andList.add(criteriaBuilder.equal(root.get("status"), CouponStatus.RECEIVED));
            andList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("availableEndDate"), endDate));

            query.where(criteriaBuilder.and(andList.toArray(new Predicate[andList.size()])));
            return query.getRestriction();
        };

        List<Coupon> couponList = couponRepository.findAll(spec);
        for (Coupon coupon : couponList) {
            coupon.setStatus(CouponStatus.EXPIRED);
        }
        couponRepository.saveAll(couponList);
    }

    // 计算使用优惠券后的价格
    public static Price calc(Price price, Coupon coupon) {
        Price newPrice = new Price(price);
        CouponTemplate template = coupon.getTemplate();
        Integer off = coupon.getOff();
        switch (template.getType()) {
        case CouponType.AMOUNT:
            newPrice.setAmount(price.getAmount() - off);
            break;
        case CouponType.PERCENT:
            newPrice.setAmount(price.getAmount() * off / 100);
            break;
        default:
        }

        if (newPrice.getAmount() <= 0) {
            newPrice.setAmount(1);
        }
        return newPrice;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void updateExpiredCouponsTask() {

        Instant endDate = toInstant(LocalDate.now().atStartOfDay());
        Specification<Coupon> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> andList = new ArrayList<>();

            andList.add(criteriaBuilder.equal(root.get("status"), CouponStatus.RECEIVED));
            andList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("availableEndDate"), endDate));

            query.where(criteriaBuilder.and(andList.toArray(new Predicate[andList.size()])));
            return query.getRestriction();
        };

        Page<Coupon> page = couponRepository.findAll(spec, PageRequest.of(0, 500));
        while (page.hasContent()) {
            List<Coupon> saveCouponList = new ArrayList<>();
            for (Coupon coupon : page.getContent()) {
                coupon.setStatus(CouponStatus.EXPIRED);
                saveCouponList.add(coupon);
            }
            couponRepository.saveAll(saveCouponList);
            page = couponRepository.findAll(spec, page.nextPageable());
        }
    }

    private CouponHandler getCouponHandler(String couponLimit) {
        CouponHandler couponHandler = (CouponHandler) springService.getBean(couponLimit + CouponHandler.HANDLER_SUFFIX);

        if (couponHandler == null) {
            couponHandler = (CouponHandler) springService.getBean("defualtCouponHandler");
        }
        return couponHandler;
    }

    private static Instant toInstant(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

}
