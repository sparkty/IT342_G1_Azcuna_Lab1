package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public String register(String username, String email, String password) {
        if(userRepository.findByUsername(username).isPresent())
            return "Username already exists";
        if(userRepository.findByEmail(email).isPresent())
            return "Email already exists";

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));

        userRepository.save(user);
        return "User registered successfully";
    }

    public String login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isEmpty())
            return "User not found";

        User user = userOpt.get();
        if(!encoder.matches(password, user.getPassword()))
            return "Invalid password";

        return "Login successful";
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
