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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teamId", referencedColumnName = "teamId")
    private TeamEntity teamEntity;

    @OneToMany(mappedBy = "userEntity")
    private List<ReservationEntity> reservations;

    public TeamEntity setTeamId(String teamId) {
        TeamEntity team = new TeamEntity();
        team.setTeamId(teamId);
        this.teamEntity = team;
        return team;
    }

}
