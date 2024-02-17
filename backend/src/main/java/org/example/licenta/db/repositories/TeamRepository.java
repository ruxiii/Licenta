package org.example.licenta.db.repositories;

import org.example.licenta.db.entities.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity, String> {
}
