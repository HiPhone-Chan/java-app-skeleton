package tech.hiphone.shop.domain.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import tech.hiphone.shop.constants.Currency;

@Embeddable
public class Price {

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "currency", length = 3)
    private String currency = Currency.CNY;

    public Price() {
    }

    public Price(Price price) {
        this.amount = price.amount;
        this.currency = price.currency;
    }

    public Price(Integer amount) {
        super();
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((currency == null) ? 0 : currency.hashCode());
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
        Price other = (Price) obj;
        if (amount == null) {
            if (other.amount != null)
                return false;
        } else if (!amount.equals(other.amount))
            return false;
        if (currency == null) {
            if (other.currency != null)
                return false;
        } else if (!currency.equals(other.currency))
            return false;
        return true;
    }

}
