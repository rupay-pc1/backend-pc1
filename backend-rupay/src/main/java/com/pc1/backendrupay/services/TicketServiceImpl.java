package com.pc1.backendrupay.services;

import com.pc1.backendrupay.domain.TicketModel;
import com.pc1.backendrupay.domain.UserModel;
import com.pc1.backendrupay.enums.TypeTicket;
import com.pc1.backendrupay.enums.TypeUser;
import com.pc1.backendrupay.exceptions.UserNotFoundException;
import com.pc1.backendrupay.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService{

    public static double LUNCH_PRICE = 11.45;
    public static double DINNER_PRICE = 11.90;

    @Autowired
    final private TicketRepository ticketRepository;

    @Autowired
    final private UserService userService;

    public TicketServiceImpl(TicketRepository ticketRepository, UserService userService) {
        this.ticketRepository = ticketRepository;
        this.userService = userService;
    }

    public List<TicketModel> listTypeTickets(){
        return ticketRepository.findAll();
    }

    public TicketModel buyTicket(UUID id, TypeTicket typeTicket) throws UserNotFoundException {
        UserModel user = userService.getUserId(id);
        if (user.getTickets() == null) {
            user.setTickets(new ArrayList<TicketModel>());
        }
        // ADMIN,
        //    STUDENT,
        //    EXTERNAL,
        //    SCHOLARSHIP_STUDENT

        if (user.getTypeUser() == TypeUser.STUDENT){
            LUNCH_PRICE = 5.72;
            DINNER_PRICE = 5.45;
        }
        else if (user.getTypeUser() == TypeUser.SCHOLARSHIP_STUDENT){
            LUNCH_PRICE = 0.0;
            DINNER_PRICE = 0.0;
        }
        else{
            LUNCH_PRICE = LUNCH_PRICE;
            DINNER_PRICE = DINNER_PRICE;
        }

        Double price = typeTicket == TypeTicket.LUNCH ? LUNCH_PRICE : DINNER_PRICE;

        TicketModel ticket = new TicketModel(price, typeTicket);
        ticketRepository.save(ticket);
        user.getTickets().add(ticket);
        userService.saveUser(user);

        return ticket;
    }
}
