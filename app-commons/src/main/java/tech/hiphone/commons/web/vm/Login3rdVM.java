package tech.hiphone.commons.web.vm;

import tech.hiphone.commons.domain.id.ModulePrincipalId;

public class Login3rdVM extends PrincipalCodeVM {

    private static final long serialVersionUID = 1L;

    private boolean rememberMe;

    public Login3rdVM() {
    }

    public Login3rdVM(ModulePrincipalId id) {
        super(id);
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

}
