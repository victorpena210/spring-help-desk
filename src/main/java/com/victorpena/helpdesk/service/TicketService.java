package com.victorpena.helpdesk.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.victorpena.helpdesk.domain.Ticket;
import com.victorpena.helpdesk.domain.TicketStatus;
import com.victorpena.helpdesk.domain.User;
import com.victorpena.helpdesk.repo.TicketRepository;
import com.victorpena.helpdesk.web.CreateTicketRequest;

@Service
public class TicketService {

    private final TicketRepository tickets;

    public TicketService(TicketRepository tickets) {
        this.tickets = tickets;
    }

    public Ticket createTicket(CreateTicketRequest request, User user) {
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setStatus(TicketStatus.NEW);
        ticket.setCreatedBy(user);
        ticket.setCreatedAt(Instant.now());
        ticket.setUpdatedAt(Instant.now());

        return tickets.save(ticket);
    }

    public List<Ticket> findTicketsForUser(User user) {
        return tickets.findByCreatedByOrderByCreatedAtDesc(user);
    }
    
    public Ticket findTicketForUser(Long id, User user) {
        return tickets.findByIdAndCreatedBy(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
    }
}