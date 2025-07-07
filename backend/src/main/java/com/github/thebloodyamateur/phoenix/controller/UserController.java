package com.github.thebloodyamateur.phoenix.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.thebloodyamateur.phoenix.dto.GeneralResponse;
import com.github.thebloodyamateur.phoenix.dto.auth.request.RolesRequest;
import com.github.thebloodyamateur.phoenix.dto.user.response.UserDTO;
import com.github.thebloodyamateur.phoenix.model.auth.Role;
import com.github.thebloodyamateur.phoenix.model.auth.User;
import com.github.thebloodyamateur.phoenix.repository.RoleRepository;
import com.github.thebloodyamateur.phoenix.repository.UserRepository;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    
    @GetMapping("/users")
    public List<String> getAllUsers() {
        // Logic to retrieve all users would go here
        List<String> users = userRepository.findAll()
                .stream()
                .map(user -> user.getUsername())
                .toList();
        return users;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        try {
            if (!userRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            return userRepository.findById(id)
                    .map(u -> new UserDTO(u.getId(), u.getUsername(), u.getFirstName(), u.getLastName()))
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
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

    @PostMapping("roles")
    public ResponseEntity<GeneralResponse> createNewRole(@RequestBody RolesRequest entity) {
        if (entity == null || entity.getRoleName() == null || entity.getRoleName().isEmpty()) {
            return ResponseEntity.badRequest().body(new GeneralResponse("Role name must not be empty"));
        }

        if (roleRepository.existsByRole(entity.getRoleName())) {
            return ResponseEntity.badRequest().body(new GeneralResponse("Error: Role already exists!"));
        }

        Role newRole = new Role(
                null,
                entity.getRoleName()
        );
        roleRepository.save(newRole);

        return ResponseEntity.created(null).body(new GeneralResponse("Role created successfully!"));
    }
}
