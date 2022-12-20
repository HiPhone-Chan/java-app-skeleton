package tech.hiphone.rbac.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import tech.hiphone.rbac.domain.id.OrganizationStaffId;

// 组织员工关系
@Entity
@Table(name = "rbac_organization_user_relation")
public class OrganizationStaffRelation {

    @EmbeddedId
    private OrganizationStaffId id;

    public OrganizationStaffId getId() {
        return id;
    }

    public void setId(OrganizationStaffId id) {
        this.id = id;
    }

}
