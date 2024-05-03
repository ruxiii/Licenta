package org.example.licenta.db.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "team")
public class TeamEntity {

    @Id
    private String teamId;

    private String teamName;

    @ManyToOne
    @JoinColumn(name = "departmentId", referencedColumnName = "departmentId")
    private DepartmentEntity departmentEntity;

    @OneToMany(mappedBy = "teamEntity")
    private List<UserEntity> users;
}
