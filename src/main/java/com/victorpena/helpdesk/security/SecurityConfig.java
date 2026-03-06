package com.victorpena.helpdesk.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/", "/api/health", "/h2-console/**").permitAll()
        .anyRequest().permitAll()
      )
      // needed so the H2 console can render in a frame
      .headers(headers -> headers.frameOptions(frame -> frame.disable()));

    return http.build();
  }
}