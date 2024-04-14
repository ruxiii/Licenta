package org.example.licenta.db.repositories;

import org.example.licenta.db.entities.AuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, String> {
    Optional<AuthenticationEntity> findByUserId(String userId);
}
