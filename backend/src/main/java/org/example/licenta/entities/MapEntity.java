package org.example.licenta.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "map")
public class MapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mapId;

    private String mapName;

    @Lob
    @Column(columnDefinition="MEDIUMBLOB")
    private String mapImage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departmentId", referencedColumnName = "departmentId")
    private DepartmentEntity departmentEntity;

    @OneToMany(mappedBy = "mapEntity")
    private List<PlaceEntity> places;
}
