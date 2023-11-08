package tech.hiphone.commons.web.vm;

import tech.hiphone.commons.domain.id.ModulePrincipalId;

public class PrincipalCodeVM extends ModulePrincipalId {

    private static final long serialVersionUID = 1L;

    private String code;

    public PrincipalCodeVM() {
    }
    
    public PrincipalCodeVM(ModulePrincipalId id) {
        super(id);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
