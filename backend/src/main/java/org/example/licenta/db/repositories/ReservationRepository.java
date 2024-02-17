package org.example.licenta.db.repositories;

import org.example.licenta.db.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
}
