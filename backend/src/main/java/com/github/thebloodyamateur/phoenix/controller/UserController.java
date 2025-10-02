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
import com.github.thebloodyamateur.phoenix.dto.RolesRequest;
import com.github.thebloodyamateur.phoenix.dto.UserRequest;
import com.github.thebloodyamateur.phoenix.dto.UserResponse;
import com.github.thebloodyamateur.phoenix.service.UserService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("")
    public ResponseEntity<GeneralResponse> registerUser(@RequestBody UserRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok().body(new GeneralResponse("User created sucessfully!"));
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable String id, @RequestBody String entity) {
        return entity;
    }

    @PatchMapping("/{id}")
    public String partiallyUpdateUser(@PathVariable String id, @RequestBody String entity) {
        return entity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new GeneralResponse("User deleted successfully!"));
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<GeneralResponse> assignRoleToUser(@PathVariable Long id, @RequestBody RolesRequest rolesRequest) {
        userService.assignRoleToUser(id, rolesRequest);
        return ResponseEntity.ok(new GeneralResponse("Role assigned to user successfully!"));
    }

    @DeleteMapping("/{id}/roles")
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

    @DeleteMapping("roles/{id}")
    public ResponseEntity<GeneralResponse> deleteRole(@PathVariable Long id) {
        userService.deleteRole(id);
        return ResponseEntity.ok(new GeneralResponse("Role deleted successfully!"));
    }
}
