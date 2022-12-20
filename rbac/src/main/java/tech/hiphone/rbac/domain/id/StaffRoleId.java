package tech.hiphone.rbac.domain.id;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import tech.hiphone.rbac.domain.Role;
import tech.hiphone.rbac.domain.Staff;

// 用户角色关系
@Embeddable
public class StaffRoleId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Staff staff;

    @ManyToOne
    private Role role;

    public StaffRoleId() {
    }

    public StaffRoleId(Staff staff, Role role) {
        super();
        this.staff = staff;
        this.role = role;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((role == null) ? 0 : role.hashCode());
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
        StaffRoleId other = (StaffRoleId) obj;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        if (staff == null) {
            if (other.staff != null)
                return false;
        } else if (!staff.equals(other.staff))
            return false;
        return true;
    }

}
