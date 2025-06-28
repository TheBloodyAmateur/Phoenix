package com.github.thebloodyamateur.phoenix.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthTokenResponse {
    private String token;
    private String message;
}
