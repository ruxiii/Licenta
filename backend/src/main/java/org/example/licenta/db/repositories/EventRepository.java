package org.example.licenta.db.repositories;

import org.example.licenta.db.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    EventEntity findByEventName(String eventName);
}
