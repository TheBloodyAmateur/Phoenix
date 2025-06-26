package com.github.thebloodyamateur.phoenix.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("")
    public String authenticateString(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
}
