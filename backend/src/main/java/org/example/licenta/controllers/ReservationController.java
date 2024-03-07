package org.example.licenta.controllers;

import org.example.licenta.dto.ReservationDto;
import org.example.licenta.exceptions.ReservationCanNotBeMadeException;
import org.example.licenta.exceptions.ReservationNotFoundException;
import org.example.licenta.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations")
    public List<ReservationDto> getReservations() throws ReservationNotFoundException {
        return reservationService.getReservations();
    }

//    TODO: implement this
//    @GetMapping("/reservations/{id}")

//    TODO: implement this
//    @DeleteMapping("/reservations/{id}")
//    public void deleteReservation(Long id) throws ReservationNotFoundException {
//        reservationService.deleteReservation(id);

    @PostMapping("/reservations/create")
    public void createReservation(@RequestBody ReservationDto reservationDto) throws ReservationCanNotBeMadeException {
        reservationService.createReservation(reservationDto);
    }

//    TODO: implement this
//    @PutMapping("/reservations/update")
}