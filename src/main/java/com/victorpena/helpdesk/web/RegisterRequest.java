package com.victorpena.helpdesk.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

    @Email(message = "Email is required")
    @NotBlank(message = "Enter a valid email address")
    private String email;
    
    @NotBlank(message = "Display name is required")
    private String displayName;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    @NotBlank(message = "Confirm Password")
    private String confirmPassword;

    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}