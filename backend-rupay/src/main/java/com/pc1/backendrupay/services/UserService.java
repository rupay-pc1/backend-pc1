package com.pc1.backendrupay.services;

import com.pc1.backendrupay.domain.UserDTO;
import com.pc1.backendrupay.domain.UserModel;
import com.pc1.backendrupay.exceptions.UserNotFoundException;
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

    public UserModel editUser(Long id, UserDTO userDTO) throws UserNotFoundException {
        checkUser(id);
        
        UserModel user = userRepository.findById(id).get();
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setTypeUser(userDTO.typeUser());
        user.setRegistration(userDTO.registration());
        System.out.println(user.getPassword());

        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) throws UserNotFoundException{
        checkUser(id);
        userRepository.deleteById(id);
    }

    private void checkUser(Long id) throws UserNotFoundException{
        if(id == null || !userRepository.existsById(id)){
            throw new UserNotFoundException("User not found");
        }
    }

}