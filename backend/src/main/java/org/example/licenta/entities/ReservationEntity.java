package org.example.licenta.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reservation")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationId;

    private LocalDateTime reservationStartDate;

    private LocalDateTime reservationEndDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "placeId", referencedColumnName = "placeId")
    private PlaceEntity placeEntity;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "eventId", referencedColumnName = "eventId")
    private EventEntity eventEntity;
}
