package org.example.licenta.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.licenta.entities.enums.PlaceName;

import java.util.List;

@Entity
@Data
@Table(name = "place")
public class PlaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long placeId;

    private PlaceName placeName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mapId", referencedColumnName = "mapId")
    private MapEntity mapEntity;

    @OneToMany(mappedBy = "placeEntity")
    private List<ReservationEntity> reservations;
}
