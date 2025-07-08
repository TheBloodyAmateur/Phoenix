package com.github.thebloodyamateur.phoenix.service.User;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.thebloodyamateur.phoenix.dto.user.request.UserRequest;
import com.github.thebloodyamateur.phoenix.dto.user.response.UserResponse;
import com.github.thebloodyamateur.phoenix.exception.ResourceNotFoundException;
import com.github.thebloodyamateur.phoenix.model.auth.User;
import com.github.thebloodyamateur.phoenix.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(),user.getFirstName(), user.getLastName()))
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

    private boolean isStringEmptyOrNull(String value) {
        return value == null || value == null ||
                value.isEmpty() || value.isEmpty();
    }
}
