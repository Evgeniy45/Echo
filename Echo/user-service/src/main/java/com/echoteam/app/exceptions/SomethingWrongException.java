package com.echoteam.app.exceptions;

public class SomethingWrongException extends RuntimeException {

    public SomethingWrongException() {
        super();
    }

    public SomethingWrongException(String message) {
        super(message);
    }

    public SomethingWrongException(String message, Throwable cause) {
        super(message, cause);
    }

    public SomethingWrongException(Throwable cause) {
        super(cause);
    }

}