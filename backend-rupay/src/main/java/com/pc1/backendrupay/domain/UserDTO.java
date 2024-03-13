package com.pc1.backendrupay.domain;

/**
 * Represents a UserDTO object.
 * 
 * This class is a record that holds information about a user.
 * It includes the user's name, email, password, type of user, and registration.
 */
public record UserDTO(String name, String email, String password, String typeUser, String registration) {
}
