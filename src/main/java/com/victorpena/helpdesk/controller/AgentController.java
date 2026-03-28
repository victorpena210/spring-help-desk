package com.victorpena.helpdesk.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.victorpena.helpdesk.domain.Ticket;
import com.victorpena.helpdesk.domain.TicketPriority;
import com.victorpena.helpdesk.domain.TicketStatus;
import com.victorpena.helpdesk.domain.User;
import com.victorpena.helpdesk.repo.UserRepository;
import com.victorpena.helpdesk.service.TicketService;

@Controller
public class AgentController {

    private final TicketService ticketService;
    private final UserRepository users;

    public AgentController(TicketService ticketService, UserRepository users) {
        this.ticketService = ticketService;
        this.users = users;
    }

    @GetMapping("/agent/tickets")
    public String agentTickets(@RequestParam(required = false) TicketStatus status,
                               @RequestParam(required = false) TicketPriority priority,
                               Model model) {

        List<Ticket> tickets = ticketService.findTicketsForAgent(status, priority);

        model.addAttribute("tickets", tickets);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedPriority", priority);
        model.addAttribute("statuses", TicketStatus.values());
        model.addAttribute("priorities", TicketPriority.values());

        return "agent-tickets";
    }

    @GetMapping("/agent/tickets/{id}")
    public String agentTicketDetails(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.findAnyTicketById(id);

        model.addAttribute("ticket", ticket);
        model.addAttribute("statuses", TicketStatus.values());

        return "agent-ticket-details";
    }

    @PostMapping("/agent/tickets/{id}/status")
    public String updateTicketStatusAsAgent(@PathVariable Long id,
                                            @RequestParam("status") TicketStatus status,
                                            RedirectAttributes redirectAttributes) {

        ticketService.updateStatusForAgent(id, status);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket status updated successfully.");

        return "redirect:/agent/tickets/" + id;
    }

    @PostMapping("/agent/tickets/{id}/assign")
    public String assignTicketToMe(@PathVariable Long id,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {

        String email = authentication.getName();

        User agent = users.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ticketService.assignTicketToAgent(id, agent);

        redirectAttributes.addFlashAttribute("successMessage", "Ticket assigned to you.");

        return "redirect:/agent/tickets/" + id;
    }
}