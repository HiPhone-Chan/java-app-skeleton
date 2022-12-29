package tech.hiphone.shop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import tech.hiphone.shop.constants.ActivityStatus;

// 活动
@Entity
@Table(name = "shop_activity")
@Inheritance(strategy = InheritanceType.JOINED)
public class ShopActivity {

    @Id
    private String id;

    @Column(name = "name", length = 63)
    private String name;

    @Column(name = "status")
    private Integer status = ActivityStatus.PROGRESSING;

    // 是否收入
    @Column(name = "is_income")
    private boolean income = true;

    // 使用量
    @Column(name = "amount")
    private Integer amount;

    // 每人参与次数
    @Column(name = "join_times")
    private Integer joinTimes;

    // 每人参与次数单位 ActivityJoinTimesUnit
    @Column(name = "join_times_unit")
    private Integer joinTimesUnit;

    @Column(name = "description", length = 63)
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getJoinTimes() {
        return joinTimes;
    }

    public void setJoinTimes(Integer joinTimes) {
        this.joinTimes = joinTimes;
    }

    public Integer getJoinTimesUnit() {
        return joinTimesUnit;
    }

    public void setJoinTimesUnit(Integer joinTimesUnit) {
        this.joinTimesUnit = joinTimesUnit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
