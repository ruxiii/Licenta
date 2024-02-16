package org.example.licenta.repositories;

import org.example.licenta.entities.LocEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocRepository extends JpaRepository<LocEntity, Long> {
}
