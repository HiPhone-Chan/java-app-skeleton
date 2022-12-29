package tech.hiphone.shop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import tech.hiphone.commons.domain.common.AbstractDescriptionEntity;
import tech.hiphone.shop.constants.CouponType;

//优惠券 模板
@Entity
@Table(name = "coupon_template")
public class CouponTemplate extends AbstractDescriptionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 优惠券类型
    @Column(name = "type")
    private Byte type = CouponType.AMOUNT;

    // 优惠券使用限制
    @Column(name = "coupon_limit", length = 31)
    private String couponLimit;
    // 订单全额退款时是否退还优惠券。
    @Column(name = "refundable")
    private Boolean refundable = false;
    // 通过该优惠券模板创建的优惠券是否有效期
    @Column(name = "expiration")
    private Boolean expiration = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Boolean getRefundable() {
        return refundable;
    }

    public void setRefundable(Boolean refundable) {
        this.refundable = refundable;
    }

    public Boolean getExpiration() {
        return expiration;
    }

    public void setExpiration(Boolean expiration) {
        this.expiration = expiration;
    }

    public String getCouponLimit() {
        return couponLimit;
    }

    public void setCouponLimit(String couponLimit) {
        this.couponLimit = couponLimit;
    }

}
