package org.example.licenta.dto;

import lombok.Data;

@Data
public class MeetingRoomReservationDto {
    private String reservationStartHour;

    private String reservationEndHour;

    private String eventName;

    private String userId;

    private Boolean flag;
}
