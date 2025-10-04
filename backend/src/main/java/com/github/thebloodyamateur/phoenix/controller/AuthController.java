package com.github.thebloodyamateur.phoenix.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.thebloodyamateur.phoenix.dto.AuthTokenResponse;
import com.github.thebloodyamateur.phoenix.dto.AuthenticationRequest;
import com.github.thebloodyamateur.phoenix.dto.GeneralResponse;
import com.github.thebloodyamateur.phoenix.service.AuthService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/auth")
@Slf4j(topic = "AuthController")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthTokenResponse> authenticateUser(@RequestBody AuthenticationRequest user) {
        log.info("Received login request for user: " + (user != null ? user.getUsername() : "null"));
        return authService.authenticateUser(user);
    }

    @GetMapping("login")
    public ResponseEntity<GeneralResponse> checkIfThereIsAnyAdmin() {
        return authService.checkIfThereIsAnyAdmin();
    }
}
