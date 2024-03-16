package org.example.licenta.db.repositories;

import org.apache.catalina.User;
import org.example.licenta.db.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findTopByOrderByUserIdDesc();
}
