package com.victorpena.helpdesk.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.victorpena.helpdesk.domain.User;
import com.victorpena.helpdesk.domain.UserRole;
import com.victorpena.helpdesk.repo.UserRepository;
import com.victorpena.helpdesk.web.RegisterRequest;

@Service
public class AuthService {
	
	private final UserRepository users;
	private final PasswordEncoder passwordEncoder;
	
	public AuthService(UserRepository users, PasswordEncoder passwordEncoder) {
		this.users = users;
		this.passwordEncoder = passwordEncoder;
	}
	
	public void register(RegisterRequest request) {
		if (users.findByEmail(request.getEmail()).isPresent()) {
			throw new IllegalArgumentException("Email is already registered");
		}
		
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new IllegalArgumentException("passwords do now match");
		}
		
		User user = new User();
		user.setEmail(request.getEmail().trim().toLowerCase());
		user.setDisplayName(request.getDisplayName().trim());
		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		user.setRole(UserRole.REQUESTER);
		user.setEnabled(true);
		
		users.save(user);
		
	}

}
