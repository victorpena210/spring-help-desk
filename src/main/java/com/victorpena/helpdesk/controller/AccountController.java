package com.victorpena.helpdesk.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.victorpena.helpdesk.domain.User;
import com.victorpena.helpdesk.repo.UserRepository;

@Controller
public class AccountController {

    private final UserRepository users;

    public AccountController(UserRepository users) {
        this.users = users;
    }

    @GetMapping("/account")
    public String account(Model model, Principal principal) {
        User user = users.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        model.addAttribute("user", user);
        return "account";
    }
}