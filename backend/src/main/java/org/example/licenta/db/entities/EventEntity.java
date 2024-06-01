package org.example.licenta.db.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "event")
public class EventEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long eventId;

        private String eventName;

        @OneToMany(mappedBy = "eventEntity")
        private List<ReservationEntity> reservationEntity;
}
