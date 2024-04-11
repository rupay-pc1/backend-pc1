package com.pc1.backendrupay.controllers;

import com.pc1.backendrupay.domain.TicketModel;
import com.pc1.backendrupay.enums.TypeTicket;
import com.pc1.backendrupay.exceptions.UserNotFoundException;
import com.pc1.backendrupay.services.TicketService;
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
        TicketModel ticket = ticketService.buyTicket(id, typeTicket);
        return new ResponseEntity<TicketModel>(ticket, HttpStatus.CREATED);
    }

    @GetMapping
    public List<TicketModel> listTickets(){
        return ticketService.listTypeTickets();
    }


}
