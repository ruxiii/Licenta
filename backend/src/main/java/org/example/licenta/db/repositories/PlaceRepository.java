package org.example.licenta.db.repositories;

import org.example.licenta.db.entities.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, String> {
}
