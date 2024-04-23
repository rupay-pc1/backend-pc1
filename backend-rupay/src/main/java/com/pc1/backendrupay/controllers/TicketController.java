package com.pc1.backendrupay.controllers;

import com.pc1.backendrupay.domain.TicketModel;
import com.pc1.backendrupay.enums.TypeTicket;
import com.pc1.backendrupay.exceptions.UserNotFoundException;
import com.pc1.backendrupay.services.TicketService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    final private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/buy/{id}/{typeTicket}")
    public ResponseEntity<?> buyTicket(@PathVariable UUID id, @PathVariable TypeTicket typeTicket) throws UserNotFoundException {
        try {
            TicketModel ticket = ticketService.buyTicket(id, typeTicket);
            return ResponseEntity.ok().body(ticket);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o pagamento");
        }
    }

    @GetMapping
    public List<TicketModel> listTickets() {
        return ticketService.listTypeTickets();
    }

    @GetMapping("/listTicketsByUserId/{id}")
    public List<TicketModel> listTicketsByUserId(@PathVariable("id") UUID id) throws UserNotFoundException {
        return ticketService.listTicketByUserId(id);
    }

    @GetMapping("/consultTicketById/{id}")
    public Optional<TicketModel> consultTicketById(@PathVariable("id") UUID id){
        return ticketService.consultTicketById(id);
    }

    @GetMapping("/listActivesTicketsByUserId/{id}")
    public List<TicketModel> listActivesTicketsByUserId(@PathVariable("id") UUID id) throws UserNotFoundException {
        return ticketService.listTicketsActives(id);
    }


}
