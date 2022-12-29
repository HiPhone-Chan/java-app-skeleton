package tech.hiphone.shop.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tech.hiphone.commons.domain.User;
import tech.hiphone.shop.constants.CouponStatus;

// 优惠券
@Entity
@Table(name = "coupon")
@Inheritance(strategy = InheritanceType.JOINED)
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Integer version;

	// 在 type 为 AMOUNT 时是金额，对应支付单位的最小值
	// 在 type 为 PERCENT 时是百分比， 金额 * OFF / 100
	@Column(name = "off")
	private Integer off;

	@Column(name = "status")
	private Byte status = CouponStatus.ACTIVED;

	@ManyToOne
	private CouponTemplate template;
	// 使用量
	@ManyToOne
	private User user;
	// 使用优惠的订单
	@ManyToOne
	private OrderInfo orderInfo;

	@Column(name = "validate_code", length = 15)
	@JsonIgnore
	private String validateCode; // 验证码

	@Column(name = "validate_date")
	private Instant validateDate; // 验证时间

	// 领取日期
	@Column(name = "receive_date")
	private Instant receiveDate;
	// 使用日期
	@Column(name = "use_date")
	private Instant useDate;
	// 有效开始日期
	@Column(name = "available_start_date")
	private Instant availableStartDate;
	// 有效结束日期
	@Column(name = "available_end_date")
	private Instant availableEndDate;

	@CreatedDate
	@Column(name = "created_date", updatable = false)
	@JsonIgnore
	private Instant createdDate = Instant.now();

	public void set(Coupon coupon) {
		this.version = coupon.version;
		this.off = coupon.off;
		this.status = coupon.status;
		this.availableStartDate = coupon.availableStartDate;
		this.availableEndDate = coupon.availableEndDate;
		this.createdDate = coupon.createdDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getOff() {
		return off;
	}

	public void setOff(Integer off) {
		this.off = off;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public CouponTemplate getTemplate() {
		return template;
	}

	public void setTemplate(CouponTemplate template) {
		this.template = template;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public Instant getValidateDate() {
		return validateDate;
	}

	public void setValidateDate(Instant validateDate) {
		this.validateDate = validateDate;
	}

	public Instant getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Instant receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Instant getUseDate() {
		return useDate;
	}

	public void setUseDate(Instant useDate) {
		this.useDate = useDate;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Instant getAvailableStartDate() {
		return availableStartDate;
	}

	public void setAvailableStartDate(Instant availableStartDate) {
		this.availableStartDate = availableStartDate;
	}

	public Instant getAvailableEndDate() {
		return availableEndDate;
	}

	public void setAvailableEndDate(Instant availableEndDate) {
		this.availableEndDate = availableEndDate;
	}

}
