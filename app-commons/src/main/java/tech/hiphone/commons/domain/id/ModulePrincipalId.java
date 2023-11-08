package tech.hiphone.commons.domain.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

// 第三方账号
public class ModulePrincipalId implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "module", length = 15)
    private String module;

    @NotNull
    @Column(name = "principal", length = 63)
    private String principal;

    public ModulePrincipalId() {
    }

    public ModulePrincipalId(String module, String principal) {
        this.module = module;
        this.principal = principal;
    }

    public ModulePrincipalId(ModulePrincipalId id) {
        this.module = id.module;
        this.principal = id.principal;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((module == null) ? 0 : module.hashCode());
        result = prime * result + ((principal == null) ? 0 : principal.hashCode());
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
        ModulePrincipalId other = (ModulePrincipalId) obj;
        if (module == null) {
            if (other.module != null)
                return false;
        } else if (!module.equals(other.module))
            return false;
        if (principal == null) {
            if (other.principal != null)
                return false;
        } else if (!principal.equals(other.principal))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("ModulePrincipalId [module=%s, principal=%s]", module, principal);
    }

}
