package tech.hiphone.shop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import tech.hiphone.commons.domain.User;
import tech.hiphone.commons.domain.common.AbstractAuditingEntity;

// 熊币使用记录
@Entity
@Table(name = "coin_record")
@Inheritance(strategy = InheritanceType.JOINED)
public class CoinRecord extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 63)
	private String name;

	// 是否收入
	@Column(name = "is_income", length = 63)
	private boolean income = true;

	// 使用量
	@Column(name = "amount")
	private Integer amount;

	@Column(name = "description", length = 63)
	private String description;
	// 使用者
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	private CoinActivity activity;

	@Version
	private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CoinActivity getActivity() {
		return activity;
	}

	public void setActivity(CoinActivity activity) {
		this.activity = activity;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
