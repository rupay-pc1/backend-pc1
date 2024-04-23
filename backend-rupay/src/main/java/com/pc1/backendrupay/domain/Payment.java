package com.pc1.backendrupay.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="user_email")
    private String userEmail;

    @Column(name = "amount")
    private double amount;
}
