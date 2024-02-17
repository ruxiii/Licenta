package org.example.licenta.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.licenta.entities.enums.DepartmentTeams;

import java.util.List;

@Entity
@Data
@Table(name = "team")
public class TeamEntity {

    @Id
    private DepartmentTeams teamId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departmentId", referencedColumnName = "departmentId")
    private DepartmentEntity departmentEntity;

    @OneToMany(mappedBy = "teamEntity")
    private List<UserEntity> users;
}
