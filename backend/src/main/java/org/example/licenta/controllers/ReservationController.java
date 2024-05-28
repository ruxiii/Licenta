package org.example.licenta.controllers;

import org.example.licenta.db.entities.PlaceEntity;
import org.example.licenta.dto.PlaceDto;
import org.example.licenta.dto.ReservationDto;
import org.example.licenta.dto.ReservationFullDto;
import org.example.licenta.exceptions.ReservationCanNotBeMadeException;
import org.example.licenta.exceptions.ReservationNotFoundException;
import org.example.licenta.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations")
    public List<ReservationFullDto> getReservations() throws ReservationNotFoundException {
        return reservationService.getReservations();
    }

//    TODO: implement this; nu i corect dar merge pentru moment
    @GetMapping("/reservations/{id}")
    public ReservationFullDto getReservation(@PathVariable String id) throws ReservationNotFoundException {
        return reservationService.getReservation(id);
    }

//    TODO: implement this; nu i corect dar merge pentru moment
    @DeleteMapping("/reservations/{id}/delete")
    public void deleteReservation(@PathVariable String id) throws ReservationNotFoundException {
        reservationService.deleteReservation(id);
    }

    @PostMapping("/{imgId}/{date}/reservation/{seatId}")
    public void createReservation(@PathVariable String imgId, @PathVariable String date, @PathVariable String seatId, @RequestBody ReservationDto reservationDto) throws ReservationCanNotBeMadeException {
        reservationService.createReservation(imgId, date, seatId, reservationDto);
    }

    @PostMapping("/{imgId}/{date}/reservation/meetingRoom/{roomId}")
    public PlaceDto createMeetingRoomReservation(@PathVariable String imgId, @PathVariable String date, @PathVariable String roomId, @RequestBody ReservationDto reservationDto) throws ReservationCanNotBeMadeException {
        return reservationService.createMeetingRoomReservation(imgId, date, roomId, reservationDto);
    }

//    TODO: implement this; nu i corect dar merge pentru moment
    @PutMapping("/reservations/{id}/update")
    public ReservationFullDto updateReservation(@PathVariable String id, @RequestBody ReservationFullDto reservationFullDto) throws ReservationNotFoundException, ReservationCanNotBeMadeException {
        return reservationService.updateReservation(id, reservationFullDto);
    }


    @GetMapping("my/reservations/{id}")
    public List<ReservationFullDto> myReservations(@PathVariable String id) throws ReservationCanNotBeMadeException, ReservationNotFoundException {
       return reservationService.myReservations(id);
    }
}