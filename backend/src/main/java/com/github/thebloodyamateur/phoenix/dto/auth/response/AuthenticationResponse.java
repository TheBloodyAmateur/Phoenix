package com.github.thebloodyamateur.phoenix.dto.auth.response;

import lombok.Getter;

@Getter
public class AuthenticationResponse {
    private String token;
    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
