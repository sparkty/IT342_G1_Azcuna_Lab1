package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register endpoint - accepts JSON body
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(
            user.getUsername(),
            user.getEmail(),
            user.getPassword()
        );
    }

    // Login endpoint - accepts JSON body
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return authService.login(
            user.getUsername(),
            user.getPassword()
        );
    }

    // Get user info - you can keep username as query param or change to path variable
    @GetMapping("/me")
    public User me(@RequestParam String username) {
        Optional<User> user = authService.getUserByUsername(username);
        return user.orElse(null);
    }
}
