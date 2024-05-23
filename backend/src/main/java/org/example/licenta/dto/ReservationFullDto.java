package org.example.licenta.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservationFullDto {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate reservationDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime reservationStartHour;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime reservationEndHour;

    private String userId;

    private String placeNameId;

    private String eventName;
}
