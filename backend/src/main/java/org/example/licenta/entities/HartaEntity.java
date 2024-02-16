package org.example.licenta.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "harta")
public class HartaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idHarta;

    private String denumireHarta;

//    vezi ce timp de camp trebuie pentru incarcat poza

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idDepartament", referencedColumnName = "idDepartament")
    private DepartamentEntity departamentEntity;

    @OneToMany(mappedBy = "hartaEntity")
    private List<LocEntity> locuri;
}
