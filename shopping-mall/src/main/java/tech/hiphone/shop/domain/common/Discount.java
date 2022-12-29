package tech.hiphone.shop.domain.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Discount {

    // 优惠吗
    @Column(name = "code")
    private String code;
    // 优惠后金额
    @Column(name = "amount")
    private Integer amount;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
