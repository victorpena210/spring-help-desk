package com.victorpena.helpdesk.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.victorpena.helpdesk.domain.User;
import com.victorpena.helpdesk.repo.UserRepository;
import com.victorpena.helpdesk.service.TicketService;
import com.victorpena.helpdesk.web.CreateTicketRequest;

import org.springframework.web.bind.annotation.PathVariable;
import com.victorpena.helpdesk.domain.Ticket;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.victorpena.helpdesk.domain.TicketStatus;


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
    public String createTicket(@jakarta.validation.Valid CreateTicketRequest request,
                               org.springframework.validation.BindingResult bindingResult,
                               Authentication authentication,
                               Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("ticket", request);
            return "new-ticket";
        }

        String email = authentication.getName();

        User user = users.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ticketService.createTicket(request, user);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket created successfully.");

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
    
    @GetMapping("/tickets/{id}")
    public String ticketDetails(@PathVariable Long id,
                                Authentication authentication,
                                Model model) {

        String email = authentication.getName();

        User user = users.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Ticket ticket = ticketService.findTicketForUser(id, user);

        model.addAttribute("ticket", ticket);
        return "ticket-details";
    }
    
    @PostMapping("/tickets/{id}/status")
    public String updateTicketStatus(@PathVariable Long id,
                                     @RequestParam("status") TicketStatus status,
                                     Authentication authentication,
                                     RedirectAttributes redirectAttributes) {

        String email = authentication.getName();

        User user = users.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ticketService.updateStatus(id, user, status);

        redirectAttributes.addFlashAttribute("successMessage", "Ticket status updated successfully.");
        return "redirect:/tickets/" + id;
    }

}