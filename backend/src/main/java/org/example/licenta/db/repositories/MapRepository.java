package org.example.licenta.db.repositories;

import org.example.licenta.db.entities.MapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<MapEntity, Long> {
}
