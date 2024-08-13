package com.phaeris.rolex.exception;

/**
 * @author wyh
 * @since 2021/4/6 17:38
 */
public class ExternalException extends RuntimeException {

    public ExternalException(String message, Object... args) {
        super(String.format(message, args));
    }

    public ExternalException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
