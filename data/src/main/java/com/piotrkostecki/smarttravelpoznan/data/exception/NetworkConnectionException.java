package com.piotrkostecki.smarttravelpoznan.data.exception;

/**
 * Exception throw by the application when a there is a network connection exception.
 */
public class NetworkConnectionException extends Exception {

    public NetworkConnectionException() {

    }

    public NetworkConnectionException(final String message) {
        super(message);
    }

    public NetworkConnectionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NetworkConnectionException(final Throwable cause) {
        super(cause);
    }
}
