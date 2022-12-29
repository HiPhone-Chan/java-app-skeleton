package tech.hiphone.shop.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import tech.hiphone.commons.domain.common.AbstractAuditingEntity;
import tech.hiphone.shop.constants.OrderStatus;
import tech.hiphone.shop.domain.common.Price;

@Entity
@Table(name = "order_info")
public class OrderInfo extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    public final static int EXPIRE_TIME = 30 * 60;

    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Version
    private Integer version;
    // OrderType (对应于商家账户分类)
    @Column(name = "type", length = 15)
    private String type;
    // 外部对应的订单id
    @Column(name = "out_order_id", length = 127)
    private String outOrderId;
    // OrderChannel
    @Column(name = "channel", length = 31)
    private String channel;
    // 支付价格
    @Embedded
    private Price price;
    // 原价
    @Column(name = "original_amount")
    private Integer originalAmount;
    // OrderStatus
    @Column(name = "status")
    private Integer status = OrderStatus.CREATED;

    @Column(name = "title", length = 127)
    private String title;

    @Column(name = "description", length = 1023)
    private String description;
    // 定价信息
    @Column(name = "pricing_info", length = 1023)
    private String pricingInfo;

    @Column(name = "extra_info", length = 4095)
    private String extraInfo;

    // 过期时间
    @Column(name = "expiration_date")
    private Instant expirationDate = Instant.now().plusSeconds(EXPIRE_TIME);

    // 处理次数
    @Column(name = "process_times")
    private Integer processTimes;
    // 用户备注
    @Column(name = "remark", length = 511)
    private String remark;

    @Column(name = "reason", length = 511)
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Integer getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Integer originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPricingInfo() {
        return pricingInfo;
    }

    public void setPricingInfo(String pricingInfo) {
        this.pricingInfo = pricingInfo;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getProcessTimes() {
        return processTimes;
    }

    public void setProcessTimes(Integer processTimes) {
        this.processTimes = processTimes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderInfo other = (OrderInfo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
