package org.example.licenta.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CompanieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long companieId;

    private String numeCompanie;
}
