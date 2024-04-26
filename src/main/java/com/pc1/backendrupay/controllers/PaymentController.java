package com.pc1.backendrupay.controllers;

import com.pc1.backendrupay.domain.RequestPaymentDTO;
import com.pc1.backendrupay.domain.TicketModel;
import com.pc1.backendrupay.domain.UserModel;
import com.pc1.backendrupay.enums.TypeTicket;
import com.pc1.backendrupay.exceptions.UserNotFoundException;
import com.pc1.backendrupay.services.TicketService;
import com.pc1.backendrupay.services.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${stripe.secretKey}")
    private String stripeSecretKey;
    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    public PaymentController(TicketService ticketService, UserService userService) {
        this.ticketService = ticketService;
        this.userService = userService;
    }
    @GetMapping("/checkout/{userId}/{typeTicket}")
    private RequestPaymentDTO hostedCheckout(@PathVariable("typeTicket") TypeTicket typeTicket, @PathVariable("userId") UUID userId) throws StripeException, UserNotFoundException {
        //TODO falta implementar uma verificação de segurança para garantir que o usuário que está fazendo a requisição tem permissão para comprar o tipo de  ticket passado
        Stripe.apiKey = stripeSecretKey;

        String student_lunch_ticket = "price_1P8q7mBo6B2t81e5yglSzDMW";
        String student_dinner_ticket = "price_1P8q8zBo6B2t81e5AooceLcg";
        String external_lunch_ticket = "price_1P8pw2Bo6B2t81e57QS53n1I";
        String external_dinner_ticket = "price_1P8q79Bo6B2t81e5O5rtwzQs";
        String scholarship_lunch_ticket = "price_1P8q9oBo6B2t81e5q31Eat9u";
        String scholarship_dinner_ticket = "price_1P8q9oBo6B2t81e5q31Eat9u";

        UserModel user = userService.getUserId(userId);

        TicketModel newTicket = ticketService.createTicket(userId, typeTicket);

        String priceId;

        switch(newTicket.getTypeTicket()){
            case STUDENT_LUNCH_TICKET -> priceId = student_lunch_ticket;
            case STUDENT_DINNER_TICKET -> priceId = student_dinner_ticket;
            case EXTERNAL_LUNCH_TICKET -> priceId = external_lunch_ticket;
            case EXTERNAL_DINNER_TICKET -> priceId = external_dinner_ticket;
            case SCHOLARSHIP_LUNCH_TICKET -> priceId = scholarship_lunch_ticket;
            case SCHOLARSHIP_DINNER_TICKET -> priceId = scholarship_dinner_ticket;
            default -> priceId = external_dinner_ticket;
        }

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPrice(priceId)
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("https://rupay-frontend.vercel.app/my-tickets")
                        .setCancelUrl("https://rupay-frontend.vercel.app/my-tickets")
                        .build();

        Session session = Session.create(params);
        RequestPaymentDTO rpDTO = new RequestPaymentDTO(session.getUrl(), userId);

        return rpDTO;
    }

}

