package org.example.licenta.services;

import org.example.licenta.db.entities.EventEntity;
import org.example.licenta.db.entities.PlaceEntity;
import org.example.licenta.db.entities.ReservationEntity;
import org.example.licenta.db.entities.UserEntity;
import org.example.licenta.db.repositories.EventRepository;
import org.example.licenta.db.repositories.PlaceRepository;
import org.example.licenta.db.repositories.ReservationRepository;
import org.example.licenta.db.repositories.UserRepository;
import org.example.licenta.dto.MeetingRoomReservationDto;
import org.example.licenta.dto.PlaceDto;
import org.example.licenta.dto.ReservationDto;
import org.example.licenta.dto.ReservationFullDto;
import org.example.licenta.exceptions.ReservationCanNotBeMadeException;
import org.example.licenta.exceptions.ReservationNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    private static final Set<String> ALLOWED_MEETING_ROOMS = Set.of(
            "JAVA-6S", "SICILY-6S", "CYPRUS-6S", "RHODES-6S", "SEYCHELLES-6S",
            "THASSOS-6S", "PALAWAN-6S", "SANTORINI-6N", "SARDINIA-6N",
            "CRETA-6N", "COMOROS-6N", "MALLORCA-5N", "CAPRI-5N", "NAXOS-5N",
            "CORSICA-5N", "CURACAO-5N", "PAROS-5N", "GUADELOUPE-5N",
            "GALAPAGOS-5N", "SECZONE2-5N"
    );


    public List<ReservationFullDto> getReservations() throws ReservationNotFoundException {
        if (reservationRepository.findAll().isEmpty()) {
            throw new ReservationNotFoundException("No reservations found");
        } else {
            List<ReservationEntity> reservations = reservationRepository.findAll();
            List<ReservationFullDto> reservationDtos = new ArrayList<>();
            for (ReservationEntity reservationEntity : reservations) {
                String userId = reservationEntity.getUserEntity().getUserId();
                String placeId = reservationEntity.getPlaceEntity().getPlaceNameId();
                String eventName = reservationEntity.getEventEntity().getEventName();
                ReservationFullDto reservationDto = new ReservationFullDto();
                reservationDto.setReservationId(String.valueOf(reservationEntity.getReservationId()));
                reservationDto.setReservationDate(reservationEntity.getReservationDate());
                reservationDto.setReservationStartHour(reservationEntity.getReservationStartHour());
                reservationDto.setReservationEndHour(reservationEntity.getReservationEndHour());
                reservationDto.setUserId(userId);
                reservationDto.setPlaceNameId(placeId);
                reservationDto.setEventName(eventName);
                reservationDtos.add(reservationDto);
            }
            return reservationDtos;
        }
    }

    //    TODO: nu e prea corect dar merge pentru moment
    public ReservationFullDto getReservation(String id) throws ReservationNotFoundException {
        if (reservationRepository.findById(Long.valueOf(id)).isEmpty()) {
            throw new ReservationNotFoundException("Reservation not found");
        } else {
            ReservationEntity reservationEntity = reservationRepository.findById(Long.valueOf(id)).get();
            String userId = reservationEntity.getUserEntity().getUserId();
            String placeId = reservationEntity.getPlaceEntity().getPlaceNameId();
            String eventName = reservationEntity.getEventEntity().getEventName();
            ReservationFullDto reservationDto = new ReservationFullDto();
            reservationDto.setReservationDate(reservationEntity.getReservationDate());
            reservationDto.setReservationStartHour(reservationEntity.getReservationStartHour());
            reservationDto.setReservationEndHour(reservationEntity.getReservationEndHour());
            reservationDto.setUserId(userId);
            reservationDto.setPlaceNameId(placeId);
            reservationDto.setEventName(eventName);
            return reservationDto;
        }
    }

    //    TODO: nu e prea corect dar merge pentru moment
    public void deleteReservation(String id) throws ReservationNotFoundException {
        if (reservationRepository.findById(Long.valueOf(id)).isEmpty()) {
            throw new ReservationNotFoundException("Reservation not found");
        } else {
            reservationRepository.deleteById(Long.valueOf(id));
        }
    }

    public void createReservation(String imgId, String date, String seatId, ReservationDto reservationDto) throws ReservationCanNotBeMadeException {
        if (userRepository.findById(reservationDto.getUserId()).isEmpty()) {
            throw new ReservationCanNotBeMadeException("User not found");
        }

        Optional<PlaceEntity> placeOptional = placeRepository.findById(seatId);
        if (placeOptional.isEmpty()) {
            throw new ReservationCanNotBeMadeException("Place not found");
        }

        UserEntity userEntity = userRepository.findById(reservationDto.getUserId()).get();
        PlaceEntity placeEntity = placeOptional.get();

        String eventName = reservationDto.getEventName();
        EventEntity eventEntity = eventRepository.findByEventName(eventName);
        if (eventEntity == null) {
            throw new ReservationCanNotBeMadeException("Event not found");
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate reservationDate;
        try {
            reservationDate = LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new ReservationCanNotBeMadeException("Invalid date format");
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime reservationStartHour;
        LocalTime reservationEndHour;
        try {
            reservationStartHour = LocalTime.parse(reservationDto.getReservationStartHour(), timeFormatter);
            reservationEndHour = LocalTime.parse(reservationDto.getReservationEndHour(), timeFormatter);
        } catch (DateTimeParseException e) {
            throw new ReservationCanNotBeMadeException("Invalid time format");
        }

        List<ReservationEntity> existingReservations = reservationRepository.findByPlaceEntityAndReservationDate(placeEntity, reservationDate);

        for (ReservationEntity existingReservation : existingReservations) {
            LocalTime existingStartHour = existingReservation.getReservationStartHour();
            LocalTime existingEndHour = existingReservation.getReservationEndHour();

            if (reservationStartHour.isBefore(existingEndHour) && reservationEndHour.isAfter(existingStartHour)) {
                LocalTime availableUntil = existingStartHour;
                throw new ReservationCanNotBeMadeException("Place only available until " + availableUntil);
            }
        }

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setReservationDate(reservationDate);
        reservationEntity.setReservationStartHour(reservationStartHour);
        reservationEntity.setReservationEndHour(reservationEndHour);
        reservationEntity.setUserEntity(userEntity);
        reservationEntity.setPlaceEntity(placeEntity);
        reservationEntity.setEventEntity(eventEntity);

        reservationRepository.save(reservationEntity);
    }

    public ReservationFullDto updateReservation(String id, ReservationFullDto reservationDto) throws ReservationNotFoundException, ReservationCanNotBeMadeException {
        if (reservationRepository.findById(Long.valueOf(id)).isEmpty()) {
            throw new ReservationNotFoundException("Reservation not found");
        } else {
            if (userRepository.findById(reservationDto.getUserId()).isEmpty()) {
                throw new ReservationCanNotBeMadeException("User not found");
            } else {
                if (placeRepository.findById(reservationDto.getPlaceNameId()).isEmpty()) {
                    throw new ReservationCanNotBeMadeException("Place not found");
                } else {
                    UserEntity userEntity = userRepository.findById(reservationDto.getUserId()).get();
                    PlaceEntity placeEntity = placeRepository.findById(reservationDto.getPlaceNameId()).get();

                    String eventName = reservationDto.getEventName();
                    EventEntity eventEntity = eventRepository.findByEventName(eventName);

                    if (eventEntity == null) {
                        throw new ReservationCanNotBeMadeException("Event not found");
                    } else {
                        ReservationEntity reservationEntity = reservationRepository.findById(Long.valueOf(id)).get();
                        reservationEntity.setReservationDate(reservationDto.getReservationDate());
                        reservationEntity.setReservationStartHour(reservationDto.getReservationStartHour());
                        reservationEntity.setReservationEndHour(reservationDto.getReservationEndHour());
                        reservationEntity.setUserEntity(userEntity);
                        reservationEntity.setPlaceEntity(placeEntity);
                        reservationEntity.setEventEntity(eventEntity);
                        reservationRepository.save(reservationEntity);

                        ReservationFullDto updatedReservationDto = new ReservationFullDto();
                        updatedReservationDto.setReservationDate(reservationEntity.getReservationDate());
                        updatedReservationDto.setReservationStartHour(reservationEntity.getReservationStartHour());
                        updatedReservationDto.setReservationEndHour(reservationEntity.getReservationEndHour());
                        updatedReservationDto.setUserId(userEntity.getUserId());
                        updatedReservationDto.setPlaceNameId(placeEntity.getPlaceNameId());
                        updatedReservationDto.setEventName(eventEntity.getEventName());
                        return updatedReservationDto;
                    }
                }
            }
        }
    }

    public List<ReservationFullDto> myReservations(String userId) throws ReservationNotFoundException {
        if (userRepository.findById(userId).isEmpty()) {
            throw new ReservationNotFoundException("User not found");
        } else {
            UserEntity userEntity = userRepository.findById(userId).get();
            List<ReservationEntity> reservations = userEntity.getReservations();
            List<ReservationFullDto> reservationDtos = new ArrayList<>();
            for (ReservationEntity reservationEntity : reservations) {
                String placeId = reservationEntity.getPlaceEntity().getPlaceNameId();
                String eventName = reservationEntity.getEventEntity().getEventName();
                ReservationFullDto reservationDto = new ReservationFullDto();
                reservationDto.setReservationId(String.valueOf(reservationEntity.getReservationId()));
                reservationDto.setReservationDate(reservationEntity.getReservationDate());
                reservationDto.setReservationStartHour(reservationEntity.getReservationStartHour());
                reservationDto.setReservationEndHour(reservationEntity.getReservationEndHour());
                reservationDto.setUserId(userId);
                reservationDto.setPlaceNameId(placeId);
                reservationDto.setEventName(eventName);
                reservationDtos.add(reservationDto);
            }
            return reservationDtos;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    public PlaceDto createMeetingRoomReservation(String imgId, String date, String roomId, MeetingRoomReservationDto meetingRoomReservationDto) throws ReservationCanNotBeMadeException {
        logger.info("Starting reservation creation process for userId: {}", meetingRoomReservationDto.getUserId());

        if (!ALLOWED_MEETING_ROOMS.contains(roomId)) {
            logger.error("Room ID {} is not allowed for reservations", roomId);
            throw new ReservationCanNotBeMadeException("This meeting room is not available for reservations.");
        }

        if (userRepository.findById(meetingRoomReservationDto.getUserId()).isEmpty()) {
            logger.error("User not found");
            throw new ReservationCanNotBeMadeException("User not found");
        }

        Optional<PlaceEntity> placeOptional = placeRepository.findById(roomId);
        if (placeOptional.isEmpty()) {
            logger.error("Place not found");
            throw new ReservationCanNotBeMadeException("Place not found");
        }

        UserEntity userEntity = userRepository.findById(meetingRoomReservationDto.getUserId()).get();
        PlaceEntity placeEntity = placeOptional.get();

        String eventName = meetingRoomReservationDto.getEventName();
        EventEntity eventEntity = eventRepository.findByEventName(eventName);
        if (eventEntity == null) {
            logger.error("Event not found");
            throw new ReservationCanNotBeMadeException("Event not found");
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate reservationDate;
        try {
            reservationDate = LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format: {}", date);
            throw new ReservationCanNotBeMadeException("Invalid date format");
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime reservationStartHour;
        LocalTime reservationEndHour;
        try {
            reservationStartHour = LocalTime.parse(meetingRoomReservationDto.getReservationStartHour(), timeFormatter);
            reservationEndHour = LocalTime.parse(meetingRoomReservationDto.getReservationEndHour(), timeFormatter);
        } catch (DateTimeParseException e) {
            logger.error("Invalid time format: {} - {}", meetingRoomReservationDto.getReservationStartHour(), meetingRoomReservationDto.getReservationEndHour());
            throw new ReservationCanNotBeMadeException("Invalid time format");
        }

        if (!isPlaceAvailable(placeEntity, reservationDate, reservationStartHour, reservationEndHour)) {
            logger.info("Place not available, searching for alternative");
            placeEntity = findOptimalAlternativePlace(reservationDate, reservationStartHour, reservationEndHour)
                    .orElseThrow(() -> {
                        logger.error("No available places for the requested time interval");
                        return new ReservationCanNotBeMadeException("No available places for the requested time interval");
                    });
        }
        if(meetingRoomReservationDto.getFlag()) {
            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setReservationDate(reservationDate);
            reservationEntity.setReservationStartHour(reservationStartHour);
            reservationEntity.setReservationEndHour(reservationEndHour);
            reservationEntity.setUserEntity(userEntity);
            reservationEntity.setPlaceEntity(placeEntity);
            reservationEntity.setEventEntity(eventEntity);

            reservationRepository.save(reservationEntity);
        }

        logger.info("Reservation created successfully for userId: {}", meetingRoomReservationDto.getUserId());

        PlaceDto placeDto = new PlaceDto();
        placeDto.setPlaceNameId(placeEntity.getPlaceNameId());
        placeDto.setMapNameId(placeEntity.getMapEntity().getMapNameId());
        return placeDto;
    }


    private boolean isPlaceAvailable(PlaceEntity placeEntity, LocalDate date, LocalTime startHour, LocalTime endHour) {
        List<ReservationEntity> existingReservations = reservationRepository.findByPlaceEntityAndReservationDate(placeEntity, date);
        for (ReservationEntity existingReservation : existingReservations) {
            LocalTime existingStartHour = existingReservation.getReservationStartHour();
            LocalTime existingEndHour = existingReservation.getReservationEndHour();
            if (startHour.isBefore(existingEndHour) && endHour.isAfter(existingStartHour)) {
                return false;
            }
        }
        return true;
    }

    private Optional<PlaceEntity> findOptimalAlternativePlace(LocalDate date, LocalTime startHour, LocalTime endHour) {
        List<PlaceEntity> allPlaces = placeRepository.findAll();

        // Filter only the allowed meeting rooms
        List<PlaceEntity> allowedMeetingRooms = allPlaces.stream()
                .filter(place -> ALLOWED_MEETING_ROOMS.contains(place.getPlaceNameId())) // Assuming PlaceEntity has a method getRoomId() to get the room ID
                .collect(Collectors.toList());

        // Sort allowed meeting rooms based on some heuristic that represents their optimal usage
        allowedMeetingRooms.sort((place1, place2) -> {
            int availability1 = calculateAvailabilityScore(place1, date, startHour, endHour);
            int availability2 = calculateAvailabilityScore(place2, date, startHour, endHour);
            return Integer.compare(availability1, availability2);
        });

        for (PlaceEntity place : allowedMeetingRooms) {
            if (isPlaceAvailable(place, date, startHour, endHour)) {
                return Optional.of(place);
            }
        }
        return Optional.empty();
    }


    private int calculateAvailabilityScore(PlaceEntity placeEntity, LocalDate date, LocalTime startHour, LocalTime endHour) {
        List<ReservationEntity> existingReservations = reservationRepository.findByPlaceEntityAndReservationDate(placeEntity, date);
        int score = 0;
        for (ReservationEntity reservation : existingReservations) {
            LocalTime existingStartHour = reservation.getReservationStartHour();
            LocalTime existingEndHour = reservation.getReservationEndHour();
            if (startHour.isBefore(existingEndHour) && endHour.isAfter(existingStartHour)) {
                score++;
            }
        }
        return score;
    }
}
