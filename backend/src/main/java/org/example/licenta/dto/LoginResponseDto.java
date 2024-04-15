package org.example.licenta.dto;

import lombok.Data;
import org.example.licenta.db.entities.AuthenticationEntity;

@Data
public class LoginResponseDto {
    private AuthenticationEntity authenticationEntity;

    private String jwt;

    public LoginResponseDto() {
        super();
    }

    public LoginResponseDto(AuthenticationEntity authenticationEntity, String jwt) {
        this.authenticationEntity = authenticationEntity;
        this.jwt = jwt;
    }
}
