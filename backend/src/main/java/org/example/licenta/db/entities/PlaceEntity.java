package org.example.licenta.db.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "place")
public class PlaceEntity {

    @Id
    private String placeNameId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mapNameId", referencedColumnName = "mapNameId")
    private MapEntity mapEntity;

    @OneToMany(mappedBy = "placeEntity")
    private List<ReservationEntity> reservations;
}
