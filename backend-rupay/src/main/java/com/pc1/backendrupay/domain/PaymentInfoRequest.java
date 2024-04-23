package com.pc1.backendrupay.domain;

import lombok.Data;

@Data
public class PaymentInfoRequest {
    private int amount;
    private String currency;
    private String receiptEmail;
}