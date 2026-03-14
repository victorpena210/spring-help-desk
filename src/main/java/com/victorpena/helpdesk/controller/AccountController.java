package com.victorpena.helpdesk.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.victorpena.helpdesk.domain.Ticket;
import com.victorpena.helpdesk.domain.TicketStatus;
import com.victorpena.helpdesk.domain.User;
import com.victorpena.helpdesk.repo.UserRepository;
import com.victorpena.helpdesk.service.TicketService;

@Controller
public class AccountController {

    private final UserRepository users;
    private final TicketService ticketService;

    public AccountController(UserRepository users, TicketService ticketService) {
        this.users = users;
        this.ticketService = ticketService;
    }
    
    

    @GetMapping("/account")
    public String account(Model model, Principal principal) {
        User user = users.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        List<Ticket> tickets = ticketService.findTicketsForUser(user);
        
        long totalTickets = tickets.size();
        long newTickets = tickets.stream()
        		.filter(ticket -> ticket.getStatus() == TicketStatus.NEW)
        		.count();

        model.addAttribute("user", user);
        model.addAttribute("tickets", tickets);
        model.addAttribute("totalTickets", totalTickets);
        model.addAttribute("newTickets", newTickets);
        
        return "account";
    }
}