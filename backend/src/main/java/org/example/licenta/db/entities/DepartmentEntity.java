package org.example.licenta.db.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "department")
public class DepartmentEntity {

    @Id
    @Column(unique=true)
    private String departmentId;

    private String departmentName;

    @OneToOne(mappedBy = "departmentEntity")
    private MapEntity mapEntity;

    @OneToMany(mappedBy = "departmentEntity")
    private List<TeamEntity> teams;
}
