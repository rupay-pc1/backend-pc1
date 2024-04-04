package com.pc1.backendrupay.services;

import com.pc1.backendrupay.domain.TicketModel;
import com.pc1.backendrupay.enums.TypeTicket;
import com.pc1.backendrupay.exceptions.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    public TicketModel buyTicket(UUID id, TypeTicket typeTicket) throws UserNotFoundException;
    public List<TicketModel> listTypeTickets();
}
