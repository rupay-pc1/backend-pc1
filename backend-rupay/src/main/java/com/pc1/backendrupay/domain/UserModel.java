package com.pc1.backendrupay.domain;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * This class represents the model for a user.
 */
@Entity
@Table(name = "tb_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String typeUser;
    private String registration; // Optional attribute

    /**
     * Constructor for the UserModel class that takes a UserDTO object as a parameter.
     * 
     * @param userDTO the UserDTO object containing the user data
     */
    public UserModel(UserDTO userDTO){
        this.name = userDTO.name();
        this.email = userDTO.email();
        this.password = userDTO.password();
        this.typeUser = userDTO.typeUser();
        this.registration = userDTO.registration();
    }

}
