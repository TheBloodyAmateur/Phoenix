package com.github.thebloodyamateur.phoenix.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/users")
public class UserController {
    
    @GetMapping("/users")
    public List<String> getAllUsers() {
        // Logic to retrieve all users would go here
        return Arrays.asList("User1", "User2", "User3");
    }

    @GetMapping("/user")
    public String getUser() {
        // Logic to retrieve a user would go here
        return "User details retrieved successfully!";
    }

    @PostMapping("/user")
    public String createUser() {
        // Logic to create a user would go here
        return "User created successfully!";
    }

    @PutMapping("/user/{id}")
    public String updateUser(@PathVariable String id, @RequestBody String entity) {
        return entity;
    }

    @PatchMapping("/user/{id}")
    public String partiallyUpdateUser(@PathVariable String id, @RequestBody String entity) {
        return entity;
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable String id) {
        // Logic to delete a user by ID would go here
        return "User with ID " + id + " deleted successfully!";
    }
}
