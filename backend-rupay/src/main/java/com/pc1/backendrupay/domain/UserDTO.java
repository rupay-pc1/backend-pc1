package com.pc1.backendrupay.domain;

import com.pc1.backendrupay.enums.TypeUser;

/**
 * Represents a UserDTO object.
 * 
 * This class is a record that holds information about a user.
 * It includes the user's name, email, password, type of user, and registration.
 */
public record UserDTO(String name, String email, String password, TypeUser typeUser, String registration) {}

