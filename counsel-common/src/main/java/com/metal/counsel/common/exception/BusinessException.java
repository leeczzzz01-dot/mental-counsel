package com.metal.counsel.common.exception;

import java.io.Serial;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 7849259020596089404L;
    private final int code;

    public BusinessException(String message) {
        this(500, message);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
