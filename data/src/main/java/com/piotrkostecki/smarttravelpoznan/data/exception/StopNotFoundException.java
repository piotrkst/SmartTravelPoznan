package com.piotrkostecki.smarttravelpoznan.data.exception;

/**
 * Exception throw by the application when a User search can't return a valid result.
 */
public class StopNotFoundException extends Exception {

    public StopNotFoundException() {
        super();
    }

    public StopNotFoundException(String message) {
        super(message);
    }

    public StopNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StopNotFoundException(Throwable cause) {
        super(cause);
    }
}
