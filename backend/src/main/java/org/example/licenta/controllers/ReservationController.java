package org.example.licenta.controllers;

import org.example.licenta.dto.PlaceDto;
import org.example.licenta.dto.ReservationDto;
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
    public List<ReservationDto> getReservations() throws ReservationNotFoundException {
        return reservationService.getReservations();
    }

//    TODO: implement this; nu i corect dar merge pentru moment
    @GetMapping("/reservations/{id}")
    public ReservationDto getReservation(@PathVariable String id) throws ReservationNotFoundException {
        return reservationService.getReservation(id);
    }

//    TODO: implement this; nu i corect dar merge pentru moment
    @DeleteMapping("/reservations/{id}/delete")
    public void deleteReservation(@PathVariable String id) throws ReservationNotFoundException {
        reservationService.deleteReservation(id);
    }

    @PostMapping("/reservations/create")
    public void createReservation(@RequestBody ReservationDto reservationDto) throws ReservationCanNotBeMadeException {
        reservationService.createReservation(reservationDto);
    }

//    TODO: implement this; nu i corect dar merge pentru moment
    @PutMapping("/reservations/{id}/update")
    public ReservationDto updateReservation(@PathVariable String id, @RequestBody ReservationDto reservationDto) throws ReservationNotFoundException, ReservationCanNotBeMadeException {
        return reservationService.updateReservation(id, reservationDto);
    }

    @GetMapping("/my/reservations/{id}")
    public List<ReservationDto> getMyReservations(@PathVariable String id) throws ReservationNotFoundException {
        return reservationService.getMyReservations(id);
    }
}