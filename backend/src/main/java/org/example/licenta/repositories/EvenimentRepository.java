package org.example.licenta.repositories;

import org.example.licenta.entities.EvenimentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvenimentRepository extends JpaRepository<EvenimentEntity, Long> {
}
