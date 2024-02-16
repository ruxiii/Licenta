package org.example.licenta.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.licenta.entities.enums.DenumireLoc;

import java.util.List;

@Entity
@Data
@Table(name = "loc")
public class LocEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idLoc;

    private DenumireLoc denumireLoc;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idHarta", referencedColumnName = "idHarta")
    private HartaEntity hartaEntity;

    @OneToMany(mappedBy = "locEntity")
    private List<RezervareEntity> rezervari;
}
