package com.pc1.backendrupay.services;

import com.pc1.backendrupay.domain.UserDTO;
import com.pc1.backendrupay.domain.UserModel;
import com.pc1.backendrupay.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> listUsers() {
        return userRepository.findAll();
    }

    public Optional<UserModel> listUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserModel createUser(UserDTO userDTO) {
        UserModel user = new UserModel(userDTO);
        return userRepository.save(user);

    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}