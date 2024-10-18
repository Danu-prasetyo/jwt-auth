package com.example.userAuthentication.Model;

import lombok.*;

//Step 5 auth

@Data
@NoArgsConstructor
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    // Getters and setters
}
