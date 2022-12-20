package tech.hiphone.rbac.web.vm;

import javax.validation.constraints.NotNull;

public class OrganizationStaffRelationVM {

    @NotNull
    private String organizationId;

    private String login;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
