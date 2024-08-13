package com.phaeris.rolex.exception;

/**
 * @author wyh
 * @since 2023/9/7
 */
public class ScheduleException extends ExternalException {

    public ScheduleException(String message, Throwable e) {
        super(message, e);
    }

    public ScheduleException(String message) {
        super(message);
    }
}
