package com.github.thebloodyamateur.phoenix.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
}
