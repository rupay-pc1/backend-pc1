package com.pc1.backendrupay.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class RequestPaymentDTO {

    private String urlPayment;
    private UUID idTicket;



}