package org.example.licenta.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "rezervare")
public class RezervareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRezervare;

    private LocalDateTime inceputDataRezervare;

    private LocalDateTime sfarsitDataRezervare;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUtilizator", referencedColumnName = "idUtilizator")
    private UtilizatorEntity utilizatorEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idLoc", referencedColumnName = "idLoc")
    private LocEntity locEntity;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "idEveniment", referencedColumnName = "idEveniment")
    private EvenimentEntity evenimentEntity;
}
