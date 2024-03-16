package com.pc1.backendrupay.exceptions;

/**
 * This class represents an exception that is thrown when a user with that registration already exists.
 */
public class RegistrationInUseException extends Exception{
    public RegistrationInUseException(String message) {
        super(message);
    }
}
