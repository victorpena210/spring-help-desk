package com.victorpena.helpdesk.web;

import com.victorpena.helpdesk.domain.TicketPriority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateTicketRequest {
	
	@NotBlank(message = "Title is required")
	@Size(max = 200, message = "Title must be 200 characters or less")
	private String title;
	
	@NotBlank(message = "Description is required")
	@Size(max = 4000, message = "Description must be 4000 characters or less")
	private String description;
	
	@NotNull(message = "Please select a priority")
	private TicketPriority priority;
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
	
	

}
