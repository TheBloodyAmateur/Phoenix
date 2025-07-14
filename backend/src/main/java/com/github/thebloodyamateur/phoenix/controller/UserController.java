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
    public List<UserResponse> getAllUsers() {
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

    @PostMapping("/user/{id}/roles")
    public ResponseEntity<GeneralResponse> assignRoleToUser(@PathVariable Long id, @RequestBody RolesRequest rolesRequest) {
        userService.assignRoleToUser(id, rolesRequest);
        return ResponseEntity.ok(new GeneralResponse("Role assigned to user successfully!"));
    }

    @DeleteMapping("/user/{id}/roles")
    public ResponseEntity<GeneralResponse> removeRoleFromUser(@PathVariable Long id, @RequestBody RolesRequest rolesRequest) {
        userService.removeRoleFromUser(id, rolesRequest);
        return ResponseEntity.ok(new GeneralResponse("Role removed from user successfully!"));
    }

    @PostMapping("roles")
    public ResponseEntity<GeneralResponse> createNewRole(@RequestBody RolesRequest entity) {
        userService.createNewRole(entity);
        return ResponseEntity.created(null).body(new GeneralResponse("Role created successfully!"));
    }

    @GetMapping("roles")
    public List<String> getAllRoles() {
        return userService.getAllRoles();
    }
}
