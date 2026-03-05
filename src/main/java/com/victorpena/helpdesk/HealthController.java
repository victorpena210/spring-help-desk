package com.victorpena.helpdesk;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
	
	@GetMapping("/")
	public String home() {
	    return "HelpDesk API is running ✅";
	}

}
