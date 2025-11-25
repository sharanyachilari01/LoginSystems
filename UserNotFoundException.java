package com.exceptions;

/**
 * Thrown when a user with the specified username is not found
 * in the database or user list.
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
