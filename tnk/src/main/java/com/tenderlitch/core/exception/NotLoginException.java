package com.tenderlitch.core.exception;


/**
 * 未登录访问系统资源异常
 */
public class NotLoginException extends BaseRuntimeException {

    private static final long serialVersionUID = -927028338979047567L;

    public NotLoginException() {
        super("global.error.code.not.login", new String[] {});
    }
}
