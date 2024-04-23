package com.pc1.backendrupay.services;

import com.pc1.backendrupay.domain.Payment;
import com.pc1.backendrupay.domain.PaymentInfoRequest;
import com.pc1.backendrupay.repositories.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PaymentService {
    @Value("${stripe.secretKey}")
    private String stripeSecretKey;

    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    public String createPaymentIntent(Double amount, String currency) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        PaymentIntent intent = PaymentIntent.create(new PaymentIntentCreateParams.Builder()
                .setAmount((long) (amount * 100)) // O valor deve ser em centavos
                .setCurrency(currency)
                .build());

        return intent.getId();
    }

    public PaymentIntent confirmPaymentIntentN(String intentId, String paymentMethod) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        PaymentIntentConfirmParams confirmParams = new PaymentIntentConfirmParams.Builder()
                .setPaymentMethod(paymentMethod)
                .build();

        PaymentIntent paymentIntent = PaymentIntent.retrieve(intentId);
        paymentIntent = paymentIntent.confirm(confirmParams);

        return paymentIntent;
    }

    public void confirmPaymentIntent(String paymentIntentId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        try {
            // Obtendo o PaymentIntent do Stripe
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            // Criando os parâmetros para confirmar o PaymentIntent
            PaymentIntentConfirmParams.Builder confirmParamsBuilder = PaymentIntentConfirmParams.builder();

        } catch (StripeException e) {
            throw e;
        }
    }

    public ResponseEntity<String> stripePayment(String userEmail) throws Exception {
        Payment payment = paymentRepository.findByUserEmail(userEmail);

        if (payment == null) {
            throw new Exception("Payment information is missing");
        }
        payment.setAmount(00.00);
        paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public PaymentIntent getPaymentStatus(String paymentId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        return PaymentIntent.retrieve(paymentId);
    }
}
