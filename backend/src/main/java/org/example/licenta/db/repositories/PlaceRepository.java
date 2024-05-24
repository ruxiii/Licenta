package org.example.licenta.db.repositories;

import org.example.licenta.db.entities.PlaceEntity;
import org.example.licenta.db.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, String> {
}
