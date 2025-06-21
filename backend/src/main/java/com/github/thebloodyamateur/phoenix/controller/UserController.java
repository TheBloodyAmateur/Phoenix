package com.github.thebloodyamateur.phoenix.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @PostMapping("/user")
    public String createUser() {
        // Logic to create a user would go here
        return "User created successfully!";
    }

    @GetMapping("/user")
    public String getUser() {
        // Logic to retrieve a user would go here
        return "User details retrieved successfully!";
    }
}
