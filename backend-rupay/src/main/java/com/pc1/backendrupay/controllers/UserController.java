package com.pc1.backendrupay.controllers;

import com.pc1.backendrupay.domain.UserDTO;
import com.pc1.backendrupay.domain.UserModel;
import com.pc1.backendrupay.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserModel> createUser(@RequestBody UserDTO userDTO){
        UserModel user = this.userService.createUser(userDTO);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/list")
    public List<UserModel> listUsers(){
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    public <Optional> java.util.Optional<UserModel> listUserById(@PathVariable("id") UUID id){
        return userService.listUserById(id);
    }
    
}
