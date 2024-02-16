package org.example.licenta.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "departament")
public class DepartamentEntity {

    @Id
    private String idDepartament;

    private String numeDepartament;

    @OneToOne(mappedBy = "departamentEntity")
    private HartaEntity hartaEntity;

    @OneToMany(mappedBy = "departamentEntity")
    private List<EchipaEntity> echipe;
}
