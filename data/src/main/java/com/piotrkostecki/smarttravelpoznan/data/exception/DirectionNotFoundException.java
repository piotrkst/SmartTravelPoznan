package com.piotrkostecki.smarttravelpoznan.data.exception;

/**
 * Exception throw by the application when a User search can't return a valid result.
 */
public class DirectionNotFoundException extends Exception {

    public DirectionNotFoundException() {
        super();
    }

    public DirectionNotFoundException(String message) {
        super(message);
    }

    public DirectionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectionNotFoundException(Throwable cause) {
        super(cause);
    }
}
