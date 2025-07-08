package com.github.thebloodyamateur.phoenix.controller;

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
import com.github.thebloodyamateur.phoenix.dto.user.request.UserRequest;
import com.github.thebloodyamateur.phoenix.dto.user.response.UserResponse;
import com.github.thebloodyamateur.phoenix.model.auth.Role;
import com.github.thebloodyamateur.phoenix.repository.RoleRepository;
import com.github.thebloodyamateur.phoenix.repository.UserRepository;
import com.github.thebloodyamateur.phoenix.service.User.UserService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/users")
    public List<String> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/user")
    public ResponseEntity<GeneralResponse> registerUser(@RequestBody UserRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok().body(new GeneralResponse("User created sucessfully!"));
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
    public ResponseEntity<GeneralResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new GeneralResponse("User deleted successfully!"));
    }

    @PostMapping("roles")
    public ResponseEntity<GeneralResponse> createNewRole(@RequestBody RolesRequest entity) {
        if (entity == null || entity.getRoleName() == null || entity.getRoleName().isEmpty()) {
            System.out.println("Role name is empty or null");
            return ResponseEntity.badRequest().body(new GeneralResponse("Role name must not be empty"));
        }

        if (roleRepository.existsByRole(entity.getRoleName())) {
            System.out.println("Role already exists: " + entity.getRoleName());
            return ResponseEntity.badRequest().body(new GeneralResponse("Error: Role already exists!"));
        }

        System.out.println("Creating new role: " + entity.getRoleName());

        Role newRole = new Role(
                null,
                entity.getRoleName()
        );
        roleRepository.save(newRole);
        System.out.println("Role created successfully: " + entity.getRoleName());

        return ResponseEntity.created(null).body(new GeneralResponse("Role created successfully!"));
    }

    @GetMapping("roles")
    public List<String> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<String> responseBody = roles.stream()
                .map(Role::toString)
                .toList();

        return responseBody;
    }
}
