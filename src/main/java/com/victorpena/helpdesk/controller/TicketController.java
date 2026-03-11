package com.victorpena.helpdesk.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.victorpena.helpdesk.domain.User;
import com.victorpena.helpdesk.repo.UserRepository;
import com.victorpena.helpdesk.service.TicketService;
import com.victorpena.helpdesk.web.CreateTicketRequest;

@Controller
public class TicketController {

    private final TicketService ticketService;
    private final UserRepository users;

    public TicketController(TicketService ticketService, UserRepository users) {
        this.ticketService = ticketService;
        this.users = users;
    }

    @GetMapping("/tickets/new")
    public String newTicketForm(Model model) {
        model.addAttribute("ticket", new CreateTicketRequest());
        return "new-ticket";
    }

    @PostMapping("/tickets")
    public String createTicket(CreateTicketRequest request, Authentication authentication) {
        String email = authentication.getName();

        User user = users.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ticketService.createTicket(request, user);
        return "redirect:/tickets";
    }

    @GetMapping("/tickets")
    public String myTickets(Authentication authentication, Model model) {
        String email = authentication.getName();

        User user = users.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        model.addAttribute("tickets", ticketService.findTicketsForUser(user));
        return "tickets";
    }
}