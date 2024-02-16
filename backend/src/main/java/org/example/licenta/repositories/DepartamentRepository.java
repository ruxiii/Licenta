package org.example.licenta.repositories;

import org.example.licenta.entities.DepartamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentRepository extends JpaRepository<DepartamentEntity, String> {
}
