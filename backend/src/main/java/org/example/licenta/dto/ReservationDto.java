package org.example.licenta.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ReservationDto {
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @NotNull
    private LocalDateTime eventStartDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @NotNull
    private LocalDateTime eventEndDate;

//    TODO: poate fac din frontend sa apara si numele utilizatorului nu doar id ul
    @NotNull
    private String userId;

    @NotNull
    private String placeNameId;

    private String eventName;
}
