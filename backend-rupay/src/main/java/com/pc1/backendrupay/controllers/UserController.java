package com.pc1.backendrupay.controllers;

import com.pc1.backendrupay.domain.UserDTO;
import com.pc1.backendrupay.domain.UserModel;
import com.pc1.backendrupay.exceptions.UserNotFoundException;
import com.pc1.backendrupay.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserDTO userDTO){
        UserModel user = this.userService.createUser(userDTO);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public List<UserModel> listUsers(){
        System.out.println("hi");
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    public <Optional> java.util.Optional<UserModel> listUserById(@PathVariable("id") Long id){
        return userService.listUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserModel user;

        try {
            user = userService.editUser(id, userDTO);
            System.out.println(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().body(user);
    }

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
