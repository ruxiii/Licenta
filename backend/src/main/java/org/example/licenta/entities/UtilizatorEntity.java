package org.example.licenta.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.example.licenta.entities.enums.RolUtilizator;

@Entity
@Data
public class UtilizatorEntity {

    @Id
    private String userId;

    private String numeuUtilizator;

    private String prenumeUtilizator;

    private String emailUtilizator;

    private String parolaUtilizator;

    private RolUtilizator rolUtilizator;
}
