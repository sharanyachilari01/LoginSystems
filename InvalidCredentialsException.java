package com.exceptions;

/**
 * Thrown when user authentication fails due to incorrect
 * username or password.
 */
public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
