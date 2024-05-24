package org.example.licenta.db.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Table(name = "reservation")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationId;

    @NotNull
    @Column(name = "reservation_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate reservationDate;

    @NotNull
    @Column(name = "reservation_start_hour")
    @JsonFormat(pattern = "HH:mm")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime reservationStartHour;

    @NotNull
    @Column(name = "reservation_end_hour")
    @JsonFormat(pattern = "HH:mm")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime reservationEndHour;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @NotNull
    private UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "placeNameId", referencedColumnName = "placeNameId")
    @NotNull
    private PlaceEntity placeEntity;

    @ManyToOne
    @JoinColumn(name = "eventId", referencedColumnName = "eventId")
    private EventEntity eventEntity;
}
