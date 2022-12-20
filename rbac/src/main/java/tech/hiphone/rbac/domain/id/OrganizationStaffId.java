package tech.hiphone.rbac.domain.id;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import tech.hiphone.rbac.domain.Organization;
import tech.hiphone.rbac.domain.Staff;

// 组织用户关系
@Embeddable
public class OrganizationStaffId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Organization organization;

    @ManyToOne
    private Staff staff;

    public OrganizationStaffId() {
    }

    public OrganizationStaffId(Organization organization, Staff staff) {
        super();
        this.organization = organization;
        this.staff = staff;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((organization == null) ? 0 : organization.hashCode());
        result = prime * result + ((staff == null) ? 0 : staff.hashCode());
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
        OrganizationStaffId other = (OrganizationStaffId) obj;
        if (organization == null) {
            if (other.organization != null)
                return false;
        } else if (!organization.equals(other.organization))
            return false;
        if (staff == null) {
            if (other.staff != null)
                return false;
        } else if (!staff.equals(other.staff))
            return false;
        return true;
    }

}
