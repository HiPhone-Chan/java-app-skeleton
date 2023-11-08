package tech.hiphone.commons.service.helper;

import java.util.Map;

import tech.hiphone.commons.service.ICodeHandler;

public abstract class AbstractDelegatingCodeHandler implements ICodeHandler {

    protected ICodeHandler codeHandler;

    public AbstractDelegatingCodeHandler(ICodeHandler codeHandler) {
        this.codeHandler = codeHandler;
    }

    @Override
    public boolean check(String principal) {
        return codeHandler.check(principal);
    }

    @Override
    public void send(String principal, String code, Map<String, String> params) {
        codeHandler.send(principal, code, params);
    }

}
