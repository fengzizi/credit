package com.credit.diversion.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 未设置密码异常
 * Created by Administrator on 2017/11/24.
 */
public class NoSetPasswordException extends AuthenticationException {

    public NoSetPasswordException() {
    }

    public NoSetPasswordException(String message) {
        super(message);
    }

    public NoSetPasswordException(Throwable cause) {
        super(cause);
    }

    public NoSetPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
