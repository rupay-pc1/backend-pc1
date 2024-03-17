package com.pc1.backendrupay.services;

import com.pc1.backendrupay.domain.UserDTO;
import com.pc1.backendrupay.domain.UserModel;
import com.pc1.backendrupay.exceptions.UserNotFoundException;
import com.pc1.backendrupay.token.Token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    public Optional<UserModel> getUserByName(String name);
    public List<UserModel> listUsers();
    public Optional<UserModel> listUserById(UUID id);
    public UserModel editUser(UUID id, UserDTO userDTO) throws UserNotFoundException;
    public void deleteUser(UUID id) throws UserNotFoundException;
    public Optional<UserModel> getUserByEmail(String email);
}
