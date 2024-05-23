package org.example.licenta.controllers;

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
    public void createReservation(@PathVariable String imgId, @PathVariable String date, @PathVariable String seatId, String userId, @RequestBody ReservationDto reservationDto) throws ReservationCanNotBeMadeException {
        reservationService.createReservation(imgId, date, seatId, userId, reservationDto);
    }

//    TODO: implement this; nu i corect dar merge pentru moment
    @PutMapping("/reservations/{id}/update")
    public ReservationFullDto updateReservation(@PathVariable String id, @RequestBody ReservationFullDto reservationFullDto) throws ReservationNotFoundException, ReservationCanNotBeMadeException {
        return reservationService.updateReservation(id, reservationFullDto);
    }

    @GetMapping("/my/reservations/{id}")
    public List<ReservationFullDto> getMyReservations(@PathVariable String id) throws ReservationNotFoundException {
        return reservationService.getMyReservations(id);
    }
}