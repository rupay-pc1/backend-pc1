package com.pc1.backendrupay.exceptions;

/**
 * This class represents an exception that is thrown when a user is not found.
 */
public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {

    }
}
