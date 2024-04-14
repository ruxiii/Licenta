package org.example.licenta.db.repositories;

import org.example.licenta.db.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    EventEntity findByEventName(String eventName);
}
