package org.example.licenta.repositories;

import org.example.licenta.entities.UtilizatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizatorRepository extends JpaRepository<UtilizatorEntity, String> {
}
