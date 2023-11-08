package tech.hiphone.commons.config;

import java.util.HashMap;
import java.util.Map;

public class AccountProperties {

    private final CodeInfo create = new CodeInfo();

    private final CodeInfo register = new CodeInfo();

    private final CodeInfo login = new CodeInfo();

    private final CodeInfo resetPassword = new CodeInfo();

    private final CodeInfo binding = new CodeInfo();

    private final CodeInfo unbind = new CodeInfo();

    public CodeInfo getCreate() {
        return create;
    }

    public CodeInfo getRegister() {
        return register;
    }

    public CodeInfo getLogin() {
        return login;
    }

    public CodeInfo getResetPassword() {
        return resetPassword;
    }

    public CodeInfo getBinding() {
        return binding;
    }

    public CodeInfo getUnbind() {
        return unbind;
    }

    public static class CodeInfo {

        private String type = ApplicationDefaults.keyType;

        private int validityInSeconds = ApplicationDefaults.keyValidityInSeconds;

        private Map<String, String> params = new HashMap<>();

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getValidityInSeconds() {
            return validityInSeconds;
        }

        public void setValidityInSeconds(int validityInSeconds) {
            this.validityInSeconds = validityInSeconds;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {
            this.params = params;
        }

    }
}
