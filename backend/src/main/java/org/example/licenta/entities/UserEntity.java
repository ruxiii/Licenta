package org.example.licenta.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.licenta.entities.enums.UserRole;

import java.util.List;

@Entity
@Data
@Table(name = "user")
public class UserEntity {

    @Id
    private String userId;

    private String userName;

    private String userFirstName;

    private String userEmail;

    private String userPassword;

    private UserRole userRole;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teamId", referencedColumnName = "teamId")
    private TeamEntity teamEntity;

    @OneToMany(mappedBy = "userEntity")
    private List<ReservationEntity> reservations;
}
