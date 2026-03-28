package com.victorpena.helpdesk.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.victorpena.helpdesk.domain.Ticket;
import com.victorpena.helpdesk.domain.TicketPriority;
import com.victorpena.helpdesk.domain.TicketStatus;
import com.victorpena.helpdesk.domain.User;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCreatedByOrderByCreatedAtDesc(User createdBy);

    Optional<Ticket> findByIdAndCreatedBy(Long id, User createdBy);

    @EntityGraph(attributePaths = {"createdBy", "assignedTo"})
    List<Ticket> findAllByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = {"createdBy", "assignedTo"})
    List<Ticket> findByStatusOrderByCreatedAtDesc(TicketStatus status);

    @EntityGraph(attributePaths = {"createdBy", "assignedTo"})
    List<Ticket> findByPriorityOrderByCreatedAtDesc(TicketPriority priority);

    @EntityGraph(attributePaths = {"createdBy", "assignedTo"})
    List<Ticket> findByStatusAndPriorityOrderByCreatedAtDesc(TicketStatus status, TicketPriority priority);

    @EntityGraph(attributePaths = {"createdBy", "assignedTo"})
    Optional<Ticket> findById(Long id);
}