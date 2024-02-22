package org.example.licenta.db.repositories;

import org.example.licenta.db.entities.AuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, String> {
    AuthenticationEntity findByUserId(String username);


}
