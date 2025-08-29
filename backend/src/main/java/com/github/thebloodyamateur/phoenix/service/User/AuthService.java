package com.github.thebloodyamateur.phoenix.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.thebloodyamateur.phoenix.dto.GeneralResponse;
import com.github.thebloodyamateur.phoenix.dto.auth.request.AuthenticationRequest;
import com.github.thebloodyamateur.phoenix.dto.auth.response.AuthTokenResponse;
import com.github.thebloodyamateur.phoenix.repository.UserRepository;
import com.github.thebloodyamateur.phoenix.util.JwtUtil;

@Component
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtils;

    public ResponseEntity<AuthTokenResponse> authenticateUser( AuthenticationRequest user) {
        Authentication authentication;

        if (isUsernameEmptyOrNull(user)) {
            return ResponseEntity.badRequest()
                    .body(new AuthTokenResponse("", "Username and password must not be empty"));
        }

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new AuthTokenResponse("", "Authentication failed: Bad credentials"));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new AuthTokenResponse(token, "User authenticated successfully!"));
    }

    public ResponseEntity<GeneralResponse> checkIfThereIsAnyAdmin() {
        boolean exists = userRepository.existsByUsername("admin");
        if (exists) {
            return ResponseEntity.ok(new GeneralResponse("true"));
        }
        return ResponseEntity.ok(new GeneralResponse("false"));
    }

    private boolean isUsernameEmptyOrNull(AuthenticationRequest user) {
        return user.getUsername() == null || user.getPassword() == null ||
                user.getUsername().isEmpty() || user.getPassword().isEmpty();
    }
}
