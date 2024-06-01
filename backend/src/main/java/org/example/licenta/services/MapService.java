package org.example.licenta.services;

import org.example.licenta.db.entities.MapEntity;
import org.example.licenta.db.entities.PlaceEntity;
import org.example.licenta.db.entities.ReservationEntity;
import org.example.licenta.db.repositories.MapRepository;
import org.example.licenta.db.repositories.PlaceRepository;
import org.example.licenta.db.repositories.ReservationRepository;
import org.example.licenta.dto.MapDto;
import org.example.licenta.exceptions.DepartmentNotFoundException;
import org.example.licenta.exceptions.MapAlreadyExistsException;
import org.example.licenta.exceptions.MapNotFoundException;
import org.example.licenta.exceptions.ReservationCanNotBeMadeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.time.format.DateTimeFormatter;

@Service
public class MapService {

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<MapDto> getMaps() throws MapNotFoundException {
        if (mapRepository.findAll().isEmpty()) {
            throw new MapNotFoundException("No maps found");
        }
        else {
            List<MapEntity> maps = mapRepository.findAll();
            List<MapDto> mapDtos = new ArrayList<>();
            for (MapEntity map : maps) {
                MapDto mapDto = new MapDto();
                mapDto.setMapNameId(map.getMapNameId());
                mapDto.setMapName(map.getMapName());
//                mapDto.setMapImage(map.getMapImage());
                mapDtos.add(mapDto);
            }
            return mapDtos;
        }
    }

    public List<Object> getMapById(String id, String date, String hour) throws MapNotFoundException {
        Optional<MapEntity> map = mapRepository.findById(id);
        if (map.isEmpty()) {
            throw new MapNotFoundException("Map not found");
        }
        else {
            MapEntity retrieveMapEntity = map.get();

            MapEntity img = new MapEntity();
            img.setMapName(retrieveMapEntity.getMapName());
            img.setMapType(retrieveMapEntity.getMapType());
            img.setMapImage(decompressBytes(retrieveMapEntity.getMapImage()));

            List<Object> response = new ArrayList<>();
            response.add(img);
            response.add(getAvailabilities(date, hour));
            response.add(getBookedPlaces(date));

            return response;
        }
    }

    public List<String> getAvailabilities(String dateString, String hour) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<ReservationEntity> reservations = reservationRepository.findByReservationDate(date);

        List<PlaceEntity> places = placeRepository.findAll();

        List<String> availablePlaceNames = places.stream()
                .filter(place -> isPlaceNotBooked(place, reservations, hour))
                .map(PlaceEntity::getPlaceNameId)
                .collect(Collectors.toList());

        return availablePlaceNames;
    }

    private boolean isPlaceNotBooked(PlaceEntity place, List<ReservationEntity> reservations, String hour) {
        // Ensure hour is zero-padded if necessary
        String formattedHour = String.format("%02d:%s", Integer.parseInt(hour.split(":")[0]), hour.split(":")[1]);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime hourLD;

        // Parse the formatted hour string
        hourLD = LocalTime.parse(formattedHour, timeFormatter);

        return reservations.stream()
                .noneMatch(reservation -> reservation.getPlaceEntity().equals(place) &&
                        !hourLD.isBefore(reservation.getReservationStartHour()) &&
                        !hourLD.isAfter(reservation.getReservationEndHour()));
    }


    public List<List<String>> getBookedPlaces(String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<ReservationEntity> reservations = reservationRepository.findByReservationDate(date);

        List<List<String>> bookedPlaceDetails = reservations.stream()
                .map(reservation -> {
                    String placeName = reservation.getPlaceEntity().getPlaceNameId();
                    String startHour = reservation.getReservationStartHour().toString();
                    String endHour = reservation.getReservationEndHour().toString();
                    return List.of(placeName, startHour, endHour);
                })
                .collect(Collectors.toList());

        return bookedPlaceDetails;
    }

    public void deleteMap(String id) throws MapNotFoundException {
        if (mapRepository.existsById(id)) {
            mapRepository.deleteById(id);
        }
        else {
            throw new MapNotFoundException("Map not found");
        }
    }

    public MapEntity createMap(String id, MultipartFile file) throws MapAlreadyExistsException, IOException {
        if (mapRepository.existsById(id)) {
            throw new MapAlreadyExistsException("Map already exists");
        }
        else {
            MapEntity mapEntity = new MapEntity();
            mapEntity.setMapNameId(id);
            mapEntity.setMapName(file.getOriginalFilename());
            mapEntity.setMapType(file.getContentType());
            mapEntity.setMapImage(compressBytes(file.getBytes()));

            mapRepository.save(mapEntity);
            return mapEntity;
        }
    }

    public MapDto updateMap(MapDto mapDto, String id) throws MapNotFoundException {
//        if (!mapRepository.existsById(id)) {
//            throw new MapNotFoundException("Map not found");
//        }
//        else {
//            MapEntity mapEntity = mapRepository.findById(id).get();
//            mapEntity.setMapNameId(mapDto.getMapNameId());
//            mapEntity.setMapImage(mapDto.getMapImage());
//            mapRepository.save(mapEntity);
//
//            MapDto mapDto1 = new MapDto();
//            mapDto1.setMapNameId(mapEntity.getMapNameId());
//            mapDto1.setMapImage(mapEntity.getMapImage());
//            return mapDto1;
//        }
        return null;
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}