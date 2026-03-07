package com.victorpena.helpdesk.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorpena.helpdesk.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}

