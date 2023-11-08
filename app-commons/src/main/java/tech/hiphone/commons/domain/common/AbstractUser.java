package tech.hiphone.commons.domain.common;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tech.hiphone.commons.constants.CommonsConstants;

@MappedSuperclass
public abstract class AbstractUser extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String login;

    // 用于辅助注册，忘记密码
    @Column(name = "principal", length = 255, unique = true)
    private String principal;

    @Column(name = "has_password", nullable = false)
    private boolean hasPassword = false;

    @JsonIgnore
    @NotNull
    @Size(min = 6, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "nick_name", length = 50)
    private String nickName;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey = CommonsConstants.DEFAULT_LANGUAGE;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "validate_key", length = 20)
    @JsonIgnore
    private String validateKey;

    @Column(name = "validate_expiration_date")
    private Instant validateExpirationDate;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_expiration_date")
    private Instant resetExpirationDate;

    @NotNull
    @Column(name = "locked")
    private boolean locked = false;

    @Column(name = "lock_date")
    private Instant lockDate;

    @Column(name = "failed_times")
    private Integer failedTimes = 0;

    @Column(name = "failed_date")
    private Instant failedDate;

    public AbstractUser() {
    }

    public AbstractUser(AbstractUser user) {
        super();
        this.id = user.id;
        this.login = user.login;
        this.principal = user.principal;
        this.hasPassword = user.hasPassword;
        this.password = user.password;
        this.nickName = user.nickName;
        this.activated = user.activated;
        this.langKey = user.langKey;
        this.imageUrl = user.imageUrl;
        this.locked = user.locked;
        this.lockDate = user.lockDate;
        this.failedTimes = user.failedTimes;
        this.failedDate = user.failedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public boolean isHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(boolean hasPassword) {
        this.hasPassword = hasPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getValidateKey() {
        return validateKey;
    }

    public void setValidateKey(String validateKey) {
        this.validateKey = validateKey;
    }

    public Instant getValidateExpirationDate() {
        return validateExpirationDate;
    }

    public void setValidateExpirationDate(Instant validateExpirationDate) {
        this.validateExpirationDate = validateExpirationDate;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetExpirationDate() {
        return resetExpirationDate;
    }

    public void setResetExpirationDate(Instant resetExpirationDate) {
        this.resetExpirationDate = resetExpirationDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Instant getLockDate() {
        return lockDate;
    }

    public void setLockDate(Instant lockDate) {
        this.lockDate = lockDate;
    }

    public Integer getFailedTimes() {
        return failedTimes;
    }

    public void setFailedTimes(Integer failedTimes) {
        this.failedTimes = failedTimes;
    }

    public Instant getFailedDate() {
        return failedDate;
    }

    public void setFailedDate(Instant failedDate) {
        this.failedDate = failedDate;
    }

    @Override
    public String toString() {
        return "AbstractUser [langKey=" + langKey + ", imageUrl=" + imageUrl + "]";
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractUser)) {
            return false;
        }
        return id != null && id.equals(((AbstractUser) o).id);
    }

}
