package org.example.licenta.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {
    private Long eventId;

    private String eventName;

    private LocalDateTime eventStartDate;

    private LocalDateTime eventEndDate;
}
