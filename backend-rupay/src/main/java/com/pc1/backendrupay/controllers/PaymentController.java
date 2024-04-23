package com.pc1.backendrupay.controllers;

import com.pc1.backendrupay.domain.TicketModel;
import com.pc1.backendrupay.services.PaymentService;
import com.pc1.backendrupay.services.TicketService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private TicketService ticketService;

    public PaymentController(PaymentService paymentService, TicketService ticketService) {
        this.paymentService = paymentService;
        this.ticketService = ticketService;
    }

    @PostMapping("/create-intent/{ticketId}")
    @ResponseBody
    public ResponseEntity<String> createPaymentIntent(@PathVariable UUID ticketId) {
        try {
            Optional<TicketModel> ticket = ticketService.consultTicketById(ticketId);
            if (ticket == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found");
            }
            Double price = ticket.get().getPrice();
            String paymentIntentId = paymentService.createPaymentIntent(price, "brl"); //
            return ResponseEntity.ok().body(paymentIntentId);
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment");
        }
    }

    @PostMapping("/confirm/{paymentIntentId}")
    public String confirmPayment(@PathVariable String paymentIntentId) {
        try {
            paymentService.confirmPaymentIntent(paymentIntentId);
            return "Pagamento confirmado com sucesso!";
        } catch (StripeException e) {
            return "Erro ao confirmar o pagamento: " + e.getMessage();
        }
    }

    @PostMapping("/confirm-payment-intent/{paymentIntentId}/{paymentMethodId}")
    public ResponseEntity<String> confirmPaymentIntent(@PathVariable String paymentIntentId,
                                                       @PathVariable String paymentMethodId) {
        try {
            PaymentIntent paymentIntent = paymentService.confirmPaymentIntentN(paymentIntentId, paymentMethodId);
            return new ResponseEntity<>(paymentIntent.getStatus(), HttpStatus.OK);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{paymentId}")
    public ResponseEntity<?> checkPaymentStatus(@PathVariable String paymentId) {
        try {
            PaymentIntent paymentIntent = paymentService.getPaymentStatus(paymentId);
            return ResponseEntity.ok().body(paymentIntent.getStatus());
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao verificar o status do pagamento");
        }
    }
}

