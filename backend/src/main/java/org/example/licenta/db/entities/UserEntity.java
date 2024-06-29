package org.example.licenta.db.entities;

import jakarta.persistence.*;
import lombok.Data;

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

    private String userRole;

    @ManyToOne
    @JoinColumn(name = "teamId", referencedColumnName = "teamId")
    private TeamEntity teamEntity;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEntity> reservations;
}
