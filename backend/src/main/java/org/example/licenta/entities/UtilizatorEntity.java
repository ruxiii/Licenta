package org.example.licenta.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.licenta.entities.enums.RolUtilizator;

import java.util.List;

@Entity
@Data
@Table(name = "utilizator")
public class UtilizatorEntity {

    @Id
    private String idUtilizator;

    private String numeuUtilizator;

    private String prenumeUtilizator;

    private String emailUtilizator;

    private String parolaUtilizator;

    private RolUtilizator rolUtilizator;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idEchipa", referencedColumnName = "idEchipa")
    private EchipaEntity echipaEntity;

    @OneToMany(mappedBy = "utilizatorEntity")
    private List<RezervareEntity> rezervari;
}
