package tech.hiphone.rbac.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import tech.hiphone.rbac.domain.id.NavigationRoleId;

// 组织权限关系
@Entity
@Table(name = "rbac_navigation_role_relation")
public class NavigationRoleRelation {

    @EmbeddedId
    private NavigationRoleId id;

    public NavigationRoleId getId() {
        return id;
    }

    public void setId(NavigationRoleId id) {
        this.id = id;
    }

}
