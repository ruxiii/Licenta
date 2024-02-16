package org.example.licenta.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.example.licenta.entities.enums.EchipeDepartament;

@Entity
@Data
public class EchipaEntity {

    @Id
    private EchipeDepartament idEchipa;
}
