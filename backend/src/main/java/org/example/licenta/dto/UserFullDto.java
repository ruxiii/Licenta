package org.example.licenta.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFullDto {
    private String userId;

    private String userName;

    private String userFirstName;

    private String userEmail;

    private String userPassword;

    private String userRole;

    private String teamId;

    private String token;
}
