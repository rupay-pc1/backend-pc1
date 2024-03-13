package com.pc1.backendrupay.services;

import com.pc1.backendrupay.domain.UserDTO;
import com.pc1.backendrupay.domain.UserModel;
import com.pc1.backendrupay.repositories.UserRepository;
import com.pc1.backendrupay.exceptions.UserNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class represents the service layer for managing user-related operations.
 */
@Service
public class UserService {
    private UserRepository userRepository;

    /**
     * Constructs a new UserService with the given UserRepository.
     * @param userRepository the repository for user data access
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a list of all users.
     * @return a list of UserModel objects representing the users
     */
    public List<UserModel> listUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     * @param id the ID of the user to retrieve
     * @return an Optional containing the UserModel if found, or an empty Optional if not found
     */
    public Optional<UserModel> listUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Creates a new user.
     * @param userDTO the DTO object containing the user data
     * @return the created UserModel object
     */
    public UserModel createUser(UserDTO userDTO) {
        UserModel user = new UserModel(userDTO);
        return userRepository.save(user);
    }

    /**
     * Edits an existing user.
     * @param id the ID of the user to edit
     * @param userDTO the DTO object containing the updated user data
     * @return the updated UserModel object
     * @throws UserNotFoundException if the user with the given ID is not found
     */
    public UserModel editUser(Long id, UserDTO userDTO) throws UserNotFoundException {
        checkUser(id);
        
        UserModel user = userRepository.findById(id).get();
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setTypeUser(userDTO.typeUser());
        user.setRegistration(userDTO.registration());

        return userRepository.save(user);
    }
    
    /**
     * Deletes a user by their ID.
     * @param id the ID of the user to delete
     * @throws UserNotFoundException if the user with the given ID is not found
     */
    public void deleteUser(Long id) throws UserNotFoundException{
        checkUser(id);
        userRepository.deleteById(id);
    }

    /**
     * Checks if a user with the given ID exists.
     * @param id the ID of the user to check
     * @throws UserNotFoundException if the user with the given ID is not found
     */
    private void checkUser(Long id) throws UserNotFoundException{
        if(id == null || !userRepository.existsById(id)){
            throw new UserNotFoundException("User not found");
        }
    }

}