package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    // Protected endpoint: Spring Security injects authenticated user's username
    @GetMapping("/me")
    public User me(Principal principal) {
        // principal.getName() returns the authenticated username
        Optional<User> user = authService.getUserByUsername(principal.getName());
        return user.orElse(null);
    }
}
