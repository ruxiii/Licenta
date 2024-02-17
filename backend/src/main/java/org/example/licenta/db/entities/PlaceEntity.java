package org.example.licenta.db.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "place")
public class PlaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long placeId;

    private String placeName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mapId", referencedColumnName = "mapId")
    private MapEntity mapEntity;

    @OneToMany(mappedBy = "placeEntity")
    private List<ReservationEntity> reservations;
}
