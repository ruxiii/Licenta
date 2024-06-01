package org.example.licenta.dto;

import lombok.Data;
import org.example.licenta.db.entities.AuthenticationEntity;

@Data
public class LoginResponseDto {
    private AuthenticationEntity authenticationEntity;

    private String token;

    public LoginResponseDto() {
        super();
    }

    public LoginResponseDto(AuthenticationEntity authenticationEntity, String token) {
        this.authenticationEntity = authenticationEntity;
        this.token = token;
    }
}