package com.victorpena.helpdesk.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
          .requestMatchers("/login", "/register", "/h2-console/**", "/css/**", "/js/**").permitAll()
          .anyRequest().authenticated()
        )
        .formLogin(form -> form
          .loginPage("/login")           // GET /login (your page)
          .loginProcessingUrl("/login")  // POST /login (handled by Spring Security)
          .usernameParameter("email")
          .passwordParameter("password")
          .defaultSuccessUrl("/", true)
          .failureUrl("/login?error")
          .permitAll()
        )
        .headers(headers -> headers.frameOptions(frame -> frame.disable())); // for H2 console

      return http.build();
    }
}