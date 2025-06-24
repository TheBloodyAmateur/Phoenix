package com.github.thebloodyamateur.phoenix.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/createUser")
    public String createUser() {
        // Logic to create a user would go here
        return "User created successfully!";
    }

    @GetMapping("/getUser")
    public String getUser() {
        // Logic to retrieve a user would go here
        return "User details retrieved successfully!";
    }

    @GetMapping("/calc")
    public int getBoolean() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4);
        int result = integerList.stream()
                .filter(n -> n > 4)
                .mapToInt(i -> i * 2)
                .sum();
        return result;
    }

}
