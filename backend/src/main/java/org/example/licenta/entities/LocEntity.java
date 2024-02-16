package org.example.licenta.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class LocEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long locId;

    private boolean ocupat;

    private LocalDate dataOcupare;

}
