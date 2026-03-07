package com.victorpena.helpdesk.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.victorpena.helpdesk.domain.User;
import com.victorpena.helpdesk.domain.UserRole;
import com.victorpena.helpdesk.repo.UserRepository;

@Configuration
public class DevDataSeeder {

  @Bean
  CommandLineRunner seedUsers(UserRepository users) {
    return args -> {
      users.findByEmail("requester@test.com").orElseGet(() -> {
        User u = new User();
        u.setEmail("requester@test.com");
        u.setDisplayName("Requester Demo");
        u.setRole(UserRole.REQUESTER);
        // Not used yet. We'll replace with real hashing when we build auth properly.
        u.setPasswordHash("{noop}password");
        u.setEnabled(true);
        return users.save(u);
      });
    };
  }
}