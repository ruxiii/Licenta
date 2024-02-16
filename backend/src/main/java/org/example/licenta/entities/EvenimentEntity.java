package org.example.licenta.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "eveniment")
public class EvenimentEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long idEveniment;

        private String denumireEveniment;

        private LocalDateTime inceputDataEveniment;

        private LocalDateTime sfarsitDataEveniment;

        @OneToOne(mappedBy = "evenimentEntity")
        private RezervareEntity rezervareEntity;
}
