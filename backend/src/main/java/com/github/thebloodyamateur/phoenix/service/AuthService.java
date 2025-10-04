package com.github.thebloodyamateur.phoenix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.thebloodyamateur.phoenix.dto.AuthTokenResponse;
import com.github.thebloodyamateur.phoenix.dto.AuthenticationRequest;
import com.github.thebloodyamateur.phoenix.dto.GeneralResponse;
import com.github.thebloodyamateur.phoenix.repository.UserRepository;
import com.github.thebloodyamateur.phoenix.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j(topic = "AuthService")
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtils;

    public ResponseEntity<AuthTokenResponse> authenticateUser( AuthenticationRequest user) {
        Authentication authentication;
        log.info("User attempting to authenticate: " + user.getUsername());
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (Exception e) {
            log.error("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(401).body(new AuthTokenResponse("", "Authentication failed: Bad credentials"));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new AuthTokenResponse(token, "User authenticated successfully!"));
    }

    public ResponseEntity<GeneralResponse> checkIfThereIsAnyAdmin() {
        boolean exists = userRepository.existsByUsername("admin");
        if (exists) {
            log.info("Admin user exists in the system.");
            return ResponseEntity.ok(new GeneralResponse("true"));
        }
        log.info("No admin user found in the system.");
        return ResponseEntity.ok(new GeneralResponse("false"));
    }
}
