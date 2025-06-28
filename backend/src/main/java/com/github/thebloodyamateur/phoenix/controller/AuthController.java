package com.github.thebloodyamateur.phoenix.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.thebloodyamateur.phoenix.dto.GeneralResponse;
import com.github.thebloodyamateur.phoenix.dto.auth.request.AuthenticationRequest;
import com.github.thebloodyamateur.phoenix.dto.auth.response.AuthTokenResponse;
import com.github.thebloodyamateur.phoenix.model.auth.User;
import com.github.thebloodyamateur.phoenix.repository.UserRepository;
import com.github.thebloodyamateur.phoenix.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtils;

    @PostMapping("login")
    public ResponseEntity<AuthTokenResponse> authenticateUser(@RequestBody AuthenticationRequest user) {
        Authentication authentication;

        if (user.getUsername() == null || user.getPassword() == null || 
            user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new AuthTokenResponse("", "Username and password must not be empty"));
        }

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new AuthTokenResponse("", "Authentication failed: Bad credentials"));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new AuthTokenResponse(token, "User authenticated successfully!"));
    }

    @PostMapping("/signup")
    public ResponseEntity<GeneralResponse> registerUser(@RequestBody AuthenticationRequest request) {

        System.out.println("Registering user: " + request.getUsername() + " with password: " + request.getPassword());

        if (request.getUsername() == null || request.getPassword() == null ||
            request.getUsername().isEmpty() || request.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new GeneralResponse("Username and password must not be empty "));
        }

        System.out.println("Checking if username already exists: " + request.getUsername() + " ... "  + userRepository.existsByUsername(request.getUsername()));

        if (userRepository.existsByUsername(request.getUsername())) {
            System.out.println("Username already exists: " + request.getUsername());
            return ResponseEntity.badRequest().body(new GeneralResponse("Error: Username is already taken!"));
        }

        System.out.println("Username is available, proceeding to create user.");
        
        // Create new user
        User newUser = new User(
                null,
                request.getUsername(),
                encoder.encode(request.getPassword()));
        userRepository.save(newUser);

        return ResponseEntity.created(null).body(new GeneralResponse("User registered successfully!"));
    }

}
