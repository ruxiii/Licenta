package org.example.licenta.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "event")
public class EventEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long eventId;

        private String eventName;

        private LocalDateTime eventStartDate;

        private LocalDateTime eventEndDate;

        @OneToOne(mappedBy = "eventEntity")
        private ReservationEntity reservationEntity;
}
