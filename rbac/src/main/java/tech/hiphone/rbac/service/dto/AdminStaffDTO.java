package tech.hiphone.rbac.service.dto;

import tech.hiphone.commons.domain.User;
import tech.hiphone.commons.service.dto.AdminUserDTO;

public class AdminStaffDTO extends AdminUserDTO {

    private String staffId;

    public AdminStaffDTO() {

    }

    public AdminStaffDTO(User user) {
        super(user);
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

}
