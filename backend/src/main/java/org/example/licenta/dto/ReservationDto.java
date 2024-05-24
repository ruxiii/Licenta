package org.example.licenta.dto;

import lombok.Data;

@Data
public class ReservationDto {

    private String reservationStartHour;

    private String reservationEndHour;

    private String eventName;

    private String userId;
}
