package com.pc1.backendrupay.domain;

import com.pc1.backendrupay.enums.TypeUser;

public record UserDTO(String name, String email, String password, TypeUser typeUser, String registration) {
}
