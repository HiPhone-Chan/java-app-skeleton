package tech.hiphone.commons.domain.common;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import tech.hiphone.commons.constants.AccountRelationStatus;
import tech.hiphone.commons.domain.id.ModulePrincipalId;

// 用非密码登录
@MappedSuperclass
public abstract class AbstractAccountRelation<U extends AbstractUser> extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ModulePrincipalId id;

    @ManyToOne
    private U user;

    // AccountRelationStatus
    @Column(name = "status")
    private Byte status = AccountRelationStatus.BOUND;

    // 用于验证
    @Column(name = "code", length = 31)
    private String code;

    @Column(name = "code_expiration_date")
    private Instant codeExpirationDate;

    public ModulePrincipalId getId() {
        return id;
    }

    public void setId(ModulePrincipalId id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getCodeExpirationDate() {
        return codeExpirationDate;
    }

    public void setCodeExpirationDate(Instant codeExpirationDate) {
        this.codeExpirationDate = codeExpirationDate;
    }

    public U getUser() {
        return user;
    }

    public void setUser(U user) {
        this.user = user;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

}
