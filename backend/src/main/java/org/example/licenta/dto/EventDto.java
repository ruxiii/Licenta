package org.example.licenta.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class EventDto {
//    private Long eventId;

    private String eventName;

//    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
//    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
//    private LocalDateTime eventStartDate;
//
//    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
//    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
//    private LocalDateTime eventEndDate;
}
