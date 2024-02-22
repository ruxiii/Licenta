package org.example.licenta.db.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "authentication")
public class AuthenticationEntity {
    @Id
    private String userId;

    private String userPassword;

    private String userRole;
}
