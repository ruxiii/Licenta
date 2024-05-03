package org.example.licenta.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "map")
public class MapEntity {

    @Id
    private String mapNameId;

    private String mapName;

    private String mapType;

    @Column(length = 50000000)
    private byte[] mapImage;

    @OneToMany(mappedBy = "mapEntity")
    private List<PlaceEntity> places;

    public MapEntity() {
    }

    public MapEntity(String mapName, String mapType, byte[] mapImage) {
        this.mapName = mapName;
        this.mapType = mapType;
        this.mapImage = mapImage;
    }
}
