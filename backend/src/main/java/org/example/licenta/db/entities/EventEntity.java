package org.example.licenta.db.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "event")
public class EventEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long eventId;

        private String eventName;

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
        @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
        private LocalDateTime eventStartDate;

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
        @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
        private LocalDateTime eventEndDate;

        @OneToOne(mappedBy = "eventEntity")
        private ReservationEntity reservationEntity;
}
