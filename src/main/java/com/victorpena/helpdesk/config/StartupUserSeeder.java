package com.victorpena.helpdesk.config;

import java.time.Instant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.victorpena.helpdesk.domain.User;
import com.victorpena.helpdesk.domain.UserRole;
import com.victorpena.helpdesk.repo.UserRepository;

@Component
public class StartupUserSeeder implements CommandLineRunner {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    public StartupUserSeeder(UserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String email = "agent@helpdesk.com";

        if (users.findByEmail(email).isEmpty()) {
            User agent = new User();
            agent.setEmail(email);
            agent.setDisplayName("Default Agent");
            agent.setPasswordHash(passwordEncoder.encode("Agent123!"));
            agent.setRole(UserRole.AGENT);
            agent.setEnabled(true);
            agent.setCreatedAt(Instant.now());

            users.save(agent);

            System.out.println("Seeded default agent account: " + email);
        }
    }
}