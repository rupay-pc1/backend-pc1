package com.pc1.backendrupay.controllers;

import com.pc1.backendrupay.domain.UserDTO;
import com.pc1.backendrupay.domain.UserModel;
import com.pc1.backendrupay.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    public <Optional> java.util.Optional<UserModel> listUserById(@PathVariable("id") Long id){
        return userService.listUserById(id);
    }
}
