package com.victorpena.helpdesk.domain;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tickets")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, length = 200)
	private String title;
	
	@Column(nullable = false, length = 4000)
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TicketPriority priority;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private TicketStatus status;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_id", nullable = false)
	private User createdBy;
	
	@Column(name = "created_at", nullable = false)
	private Instant createdAt = Instant.now();
	
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt = Instant.now();
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TicketPriority getPriority() {
		return priority;
	}

	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public String getCreatedAtFormatted() {
	    if (createdAt == null) return "";
	    return DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a")
	            .withZone(ZoneId.systemDefault())
	            .format(createdAt);
	}


	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAtFormatted() {
	    if (updatedAt == null) return "";
	    return DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a")
	            .withZone(ZoneId.systemDefault())
	            .format(updatedAt);
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public User getCreatedBy() {
	    return createdBy;
	}

	public void setCreatedBy(User createdBy) {
	    this.createdBy = createdBy;
	}

}
