package com.github.thebloodyamateur.phoenix.service.User;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.thebloodyamateur.phoenix.dto.auth.request.RolesRequest;
import com.github.thebloodyamateur.phoenix.dto.user.request.UserRequest;
import com.github.thebloodyamateur.phoenix.dto.user.response.UserResponse;
import com.github.thebloodyamateur.phoenix.exception.ResourceNotFoundException;
import com.github.thebloodyamateur.phoenix.model.auth.Role;
import com.github.thebloodyamateur.phoenix.model.auth.User;
import com.github.thebloodyamateur.phoenix.repository.RoleRepository;
import com.github.thebloodyamateur.phoenix.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getFirstName(),
                        user.getLastName()))
                .toList();
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with the id " + id + " was not found"));
        return new UserResponse(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());
    }

    public void registerUser(UserRequest request) {

        if (isStringEmptyOrNull(request.getUsername())) {
            throw new IllegalArgumentException("Username can not be null or empty!");
        }

        if (isStringEmptyOrNull(request.getPassword())) {
            throw new IllegalArgumentException("Password can not be null or empty!");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("User with username " + request.getUsername() + " does already exist");
        }

        User newUser = new User().builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        userRepository.save(newUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with the id " + id + " was not found");
        }
        userRepository.deleteById(id);
    }

    public void createNewRole(RolesRequest roleName) {
        if (isStringEmptyOrNull(roleName.getRoleName())) {
            throw new IllegalArgumentException("Role name must not be empty");
        }

        if (userRepository.existsByUsername(roleName.getRoleName())) {
            throw new IllegalArgumentException("Error: Role already exists!");
        }

        roleRepository.existsByName(roleName.getRoleName()).ifPresent(
            exists -> {
                if (exists) {
                    throw new IllegalArgumentException("Role with name " + roleName.getRoleName() + " already exists");
                }
            }
        );

        Role newRole = new Role(null, roleName.getRoleName());
        roleRepository.save(newRole);
    }

    public List<String> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(Role::toString)
                .toList();
    }

    private boolean isStringEmptyOrNull(String value) {
        return value == null || value == null ||
                value.isEmpty() || value.isEmpty();
    }

    public void assignRoleToUser(Long userId, RolesRequest rolesRequest) {
        if (rolesRequest == null || isStringEmptyOrNull(rolesRequest.getRoleName())) {
            throw new IllegalArgumentException("Role name must not be empty");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with the id " + userId + " was not found"));

        Role role = roleRepository.findByName(rolesRequest.getRoleName());
        if (role == null) {
            throw new ResourceNotFoundException("Role with name " + rolesRequest.getRoleName() + " does not exist");
        }

        if (user.getRoles().stream().anyMatch(r -> r.getName().equals(role.getName()))) {
            throw new IllegalArgumentException("User already has the role " + rolesRequest.getRoleName());
        }

        user.getRoles().add(role);
        userRepository.save(user);
    }

    public void removeRoleFromUser(Long userId, RolesRequest rolesRequest) {
        if (rolesRequest == null || isStringEmptyOrNull(rolesRequest.getRoleName())) {
            throw new IllegalArgumentException("Role name must not be empty");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with the id " + userId + " was not found"));

        Role role = roleRepository.findByName(rolesRequest.getRoleName());
        if (role == null) {
            throw new ResourceNotFoundException("Role with name " + rolesRequest.getRoleName() + " does not exist");
        }

        user.getRoles().remove(role);
        userRepository.save(user);
    }

    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role with the id " + id + " was not found");
        }

        if(userRepository.existsUserWithRoleId(id)) {
            throw new IllegalArgumentException("Role with id " + id + " cannot be deleted because it is assigned to one or more users");
        }

        roleRepository.deleteById(id);
    }
}
