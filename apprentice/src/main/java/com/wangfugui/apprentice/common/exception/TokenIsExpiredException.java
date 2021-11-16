package com.wangfugui.apprentice.common.exception;

/**
 * @description: 自定义异常
 * @author: MrFugui
 * @date: 2021年10月30日10:52:55
 */
public class TokenIsExpiredException extends Exception {


    public TokenIsExpiredException() {
    }

    public TokenIsExpiredException(String message) {
        super(message);
    }

    public TokenIsExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenIsExpiredException(Throwable cause) {
        super(cause);
    }

    public TokenIsExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
