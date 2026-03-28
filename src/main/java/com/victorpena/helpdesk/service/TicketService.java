package com.victorpena.helpdesk.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.victorpena.helpdesk.domain.Ticket;
import com.victorpena.helpdesk.domain.TicketPriority;
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
    
    public Ticket closeTicketForRequester(Long id, User user) {
        Ticket ticket = tickets.findByIdAndCreatedBy(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        if (ticket.getStatus() == TicketStatus.CLOSED) {
            throw new IllegalStateException("Ticket is already closed");
        }

        ticket.setStatus(TicketStatus.CLOSED);
        ticket.setUpdatedAt(Instant.now());

        return tickets.save(ticket);
    }

    public Ticket reopenTicketForRequester(Long id, User user) {
        Ticket ticket = tickets.findByIdAndCreatedBy(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        if (ticket.getStatus() != TicketStatus.CLOSED &&
            ticket.getStatus() != TicketStatus.RESOLVED) {
            throw new IllegalStateException("Only closed or resolved tickets can be reopened");
        }

        ticket.setStatus(TicketStatus.NEW);
        ticket.setUpdatedAt(Instant.now());

        return tickets.save(ticket);
    }

    
    public Ticket assignTicketToAgent(Long id, User agent) {
        Ticket ticket = tickets.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        ticket.setAssignedTo(agent);
        ticket.setUpdatedAt(Instant.now());

        return tickets.save(ticket);
    }
    
    public Ticket updateStatusForAgent(Long id, TicketStatus status) {
        Ticket ticket = tickets.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        ticket.setStatus(status);
        ticket.setUpdatedAt(Instant.now());

        return tickets.save(ticket);
    }
    
    public List<Ticket> findAllTickets() {
        return tickets.findAllByOrderByCreatedAtDesc();
    }

    public List<Ticket> findTicketsForAgent(TicketStatus status, TicketPriority priority) {
        if (status != null && priority != null) {
            return tickets.findByStatusAndPriorityOrderByCreatedAtDesc(status, priority);
        }
        if (status != null) {
            return tickets.findByStatusOrderByCreatedAtDesc(status);
        }
        if (priority != null) {
            return tickets.findByPriorityOrderByCreatedAtDesc(priority);
        }
        return tickets.findAllByOrderByCreatedAtDesc();
    }

    public Ticket findAnyTicketById(Long id) {
        return tickets.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
    }


    
    
}