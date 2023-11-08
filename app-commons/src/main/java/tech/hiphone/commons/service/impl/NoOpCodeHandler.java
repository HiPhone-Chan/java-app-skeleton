package tech.hiphone.commons.service.impl;

import java.util.Map;

import tech.hiphone.commons.service.ICodeHandler;

public class NoOpCodeHandler implements ICodeHandler {

    private final static ICodeHandler instance = new NoOpCodeHandler();

    private NoOpCodeHandler() {
    }

    @Override
    public boolean check(String principal) {
        return true;
    }

    @Override
    public void send(String principal, String code, Map<String, String> params) {
    }

    public static ICodeHandler getInstance() {
        return instance;
    }

}
