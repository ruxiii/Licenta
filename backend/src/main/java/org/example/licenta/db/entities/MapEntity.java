package org.example.licenta.db.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "map")
public class MapEntity {

    @Id
    private String mapNameId;

//    TODO: implement mapImage (nu cred ca poate fi facuta pana la frontend)
    @Lob
    @Column(columnDefinition="MEDIUMBLOB")
    private String mapImage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departmentId", referencedColumnName = "departmentId")
    private DepartmentEntity departmentEntity;

    @OneToMany(mappedBy = "mapEntity")
    private List<PlaceEntity> places;
}
