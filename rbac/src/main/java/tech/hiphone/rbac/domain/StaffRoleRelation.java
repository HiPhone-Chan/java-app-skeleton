package tech.hiphone.rbac.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import tech.hiphone.rbac.domain.id.StaffRoleId;

// 角色
@Entity
@Table(name = "user_role_relation")
public class StaffRoleRelation {

    @EmbeddedId
    private StaffRoleId id;

    public StaffRoleId getId() {
        return id;
    }

    public void setId(StaffRoleId id) {
        this.id = id;
    }

}
