package com.victorpena.helpdesk.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorpena.helpdesk.domain.Ticket;
import com.victorpena.helpdesk.domain.User;

import java.util.List;
import java.util.Optional;



public interface TicketRepository extends JpaRepository<Ticket, Long>{

	List<Ticket> findByCreatedByOrderByCreatedAtDesc(User createdBy);
	
	Optional<Ticket> findByIdAndCreatedBy(Long id, User createdBy);
	
}
