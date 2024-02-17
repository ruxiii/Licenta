package org.example.licenta.dto;

import lombok.Data;
import org.example.licenta.db.entities.enums.DepartmentTeams;
import org.example.licenta.db.entities.enums.UserRole;

@Data
public class UserDto {
    private String userId;

    private String userName;

    private String userFirstName;

    private String userEmail;

    private String userPassword;

    private UserRole userRole;

    private DepartmentTeams teamId;
}
