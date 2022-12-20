package tech.hiphone.rbac.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import tech.hiphone.rbac.domain.id.RoleApiId;

//导航使用到的api关系
@Entity
@Table(name = "rbac_role_api_relation")
public class RoleApiRelation {

    @EmbeddedId
    private RoleApiId id;

    public RoleApiId getId() {
        return id;
    }

    public void setId(RoleApiId id) {
        this.id = id;
    }

}
