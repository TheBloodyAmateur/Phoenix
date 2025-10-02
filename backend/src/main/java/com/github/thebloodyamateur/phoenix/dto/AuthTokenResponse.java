package com.github.thebloodyamateur.phoenix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthTokenResponse {
    private String token;
    private String message;
}
