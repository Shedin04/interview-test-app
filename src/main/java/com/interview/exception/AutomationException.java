package com.interview.exception;

import static java.lang.String.format;

public class AutomationException extends RuntimeException {

    public AutomationException(String message) {
        super(message);
    }

    public AutomationException(String message, String... args) {
        super(format(message, (Object[]) args));
    }

    public AutomationException(String message, Throwable cause) {
        super(message, cause);
    }
}