package org.example.licenta.services;

import org.example.licenta.db.entities.EventEntity;
import org.example.licenta.db.entities.PlaceEntity;
import org.example.licenta.db.entities.ReservationEntity;
import org.example.licenta.db.entities.UserEntity;
import org.example.licenta.db.repositories.EventRepository;
import org.example.licenta.db.repositories.PlaceRepository;
import org.example.licenta.db.repositories.ReservationRepository;
import org.example.licenta.db.repositories.UserRepository;
import org.example.licenta.dto.ReservationDto;
import org.example.licenta.exceptions.ReservationCanNotBeMadeException;
import org.example.licenta.exceptions.ReservationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private EventRepository eventRepository;

    public List<ReservationDto> getReservations() throws ReservationNotFoundException {
        if (reservationRepository.findAll().isEmpty()) {
            throw new ReservationNotFoundException("No reservations found");
        }
        else {
            List<ReservationEntity> reservations = reservationRepository.findAll();
            List<ReservationDto> reservationDtos = new ArrayList<>();
            for (ReservationEntity reservationEntity : reservations) {
                String userId = reservationEntity.getUserEntity().getUserId();
                String placeId = reservationEntity.getPlaceEntity().getPlaceNameId();
                String eventName = reservationEntity.getEventEntity().getEventName();
                ReservationDto reservationDto = new ReservationDto();
                reservationDto.setEventStartDate(reservationEntity.getReservationStartDate());
                reservationDto.setEventEndDate(reservationEntity.getReservationEndDate());
                reservationDto.setUserId(userId);
                reservationDto.setPlaceNameId(placeId);
//                reservationDto.setEventName(eventName);
                reservationDtos.add(reservationDto);
            }
            return reservationDtos;
        }
    }

    public void deleteReservation(String id) throws ReservationNotFoundException {
        if (reservationRepository.findById(Long.valueOf(id)).isEmpty()) {
            throw new ReservationNotFoundException("Reservation not found");
        } else {
            reservationRepository.deleteById(Long.valueOf(id));
        }
    }

    public void createReservation(ReservationDto reservationDto) throws ReservationCanNotBeMadeException {
        UserEntity userEntity = userRepository.findById(reservationDto.getUserId()).get();
        PlaceEntity placeEntity = placeRepository.findById(reservationDto.getPlaceNameId()).get();
        if(userEntity == null || placeEntity == null) {
            throw new ReservationCanNotBeMadeException("User or place not found");
        }
        else {
            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setReservationStartDate(reservationDto.getEventStartDate());
            reservationEntity.setReservationEndDate(reservationDto.getEventEndDate());
            reservationEntity.setUserEntity(userEntity);
            reservationEntity.setPlaceEntity(placeEntity);
//          TODO: eveniment rezervare
            reservationRepository.save(reservationEntity);
        }
    }
}
