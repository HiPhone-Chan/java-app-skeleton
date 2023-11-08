package tech.hiphone.commons.exceptioin;

import java.util.function.Supplier;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String code;

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public ServiceException(String code) {
        this(code, code);
    }

    public String getCode() {
        return code;
    }

    public static Supplier<ServiceException> supplier(String code, String msg) {
        return () -> new ServiceException(code, msg);
    }

    public static Supplier<ServiceException> supplier(String code) {
        return () -> new ServiceException(code);
    }

}
