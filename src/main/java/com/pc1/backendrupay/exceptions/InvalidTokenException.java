package com.pc1.backendrupay.exceptions;

/**
 * This class represents an exception that is thrown when a token is invalid.
 */
public class InvalidTokenException extends Exception {
    public InvalidTokenException(String message) {
        super(message);
    }
}
