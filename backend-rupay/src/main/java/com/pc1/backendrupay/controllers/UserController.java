package com.pc1.backendrupay.controllers;

import com.pc1.backendrupay.domain.UserDTO;
import com.pc1.backendrupay.domain.UserModel;
import com.pc1.backendrupay.services.UserService;
import com.pc1.backendrupay.exceptions.UserNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class represents the controller layer for managing user-related operations.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    /**
     * Constructor for UserController.
     * 
     * @param userService the UserService instance to be used
     */
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Create a new user.
     * 
     * @param userDTO the UserDTO object containing the user data
     * @return a ResponseEntity with the created UserModel object
     */
    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserDTO userDTO){
        UserModel user = this.userService.createUser(userDTO);
        return ResponseEntity.ok().body(user);
    }

    /**
     * Get a list of all users.
     * 
     * @return a List of UserModel objects representing all users
     */
    @GetMapping
    public List<UserModel> listUsers(){
        return userService.listUsers();
    }

    /**
     * Get a user by their ID.
     * 
     * @param id the ID of the user to retrieve
     * @return an Optional containing the UserModel object if found, or an empty Optional if not found
     */
    @GetMapping("/{id}")
    public <Optional> java.util.Optional<UserModel> listUserById(@PathVariable("id") Long id){
        return userService.listUserById(id);
    }

    /**
     * Edit a user by their ID.
     * 
     * @param id the ID of the user to edit
     * @param userDTO the UserDTO object containing the updated user data
     * @return a ResponseEntity with the edited UserModel object if successful, 
     * or a ResponseEntity with an error message if the user is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserModel user;

        try {
            user = userService.editUser(id, userDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().body(user);
    }

    /**
     * Delete a user by their ID.
     * 
     * @param id the ID of the user to delete
     * @return a ResponseEntity with the ID of the deleted user if successful, 
     * or a ResponseEntity with an error message if the user is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        try {
            userService.deleteUser(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().body(id);
    }
    
}
