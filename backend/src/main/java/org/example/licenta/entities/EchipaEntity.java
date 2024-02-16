package org.example.licenta.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.licenta.entities.enums.EchipeDepartament;

import java.util.List;

@Entity
@Data
@Table(name = "echipa")
public class EchipaEntity {

    @Id
    private EchipeDepartament idEchipa;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idDepartament", referencedColumnName = "idDepartament")
    private DepartamentEntity departamentEntity;

    @OneToMany(mappedBy = "echipaEntity")
    private List<UtilizatorEntity> utilizatori;
}
