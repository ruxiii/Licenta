package org.example.licenta.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class DepartamentEntity {

    @Id
    private String idDepartament;

    private String numeDepartament;
}
