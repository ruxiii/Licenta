package org.example.licenta.services;

import org.example.licenta.db.entities.EventEntity;
import org.example.licenta.db.repositories.EventRepository;
import org.example.licenta.dto.EventDto;
import org.example.licenta.exceptions.EventAlreadyExistsException;
import org.example.licenta.exceptions.EventNotFoundException;
import org.example.licenta.mappers.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    private final EventMapper eventMapper;

    public EventService(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public List<EventDto> getEvents() throws EventNotFoundException {
        if (eventRepository.findAll().isEmpty()) {
            throw new EventNotFoundException("No events found");
        }
        else{
            List<EventEntity> events = eventRepository.findAll();
            return events.stream().map(eventMapper::toDto).collect(Collectors.toList());
        }
    }

    public EventDto getEventById(String id) throws EventNotFoundException {
        Optional<EventEntity> event = eventRepository.findById(Long.valueOf(id));
        if (event.isEmpty()) {
            throw new EventNotFoundException("Event not found");
        }
        else {
            return eventMapper.toDto(event.get());
        }
    }

    public void deleteEvent(String id) throws EventNotFoundException {
        if (eventRepository.existsById(Long.valueOf(id))) {
            eventRepository.deleteById(Long.valueOf(id));
        }
        else {
            throw new EventNotFoundException("Event not found");
        }
    }

    public void createEvent(EventDto eventDto) throws EventAlreadyExistsException {
        if (eventRepository.existsById(eventDto.getEventId())) {
            throw new EventAlreadyExistsException("Event already exists");
        }
        else {
            eventRepository.save(eventMapper.toEntity(eventDto));
        }
    }

    public EventDto updateEvent(EventDto eventDto, String id) throws EventNotFoundException {
        if (!eventRepository.existsById(Long.valueOf(id))) {
            throw new EventNotFoundException("Event not found");
        }
        else {
            EventEntity eventEntity = eventRepository.findById(Long.valueOf(id)).get();
            eventEntity.setEventId(eventDto.getEventId());
            eventEntity.setEventName(eventDto.getEventName());
            eventEntity.setEventStartDate(eventDto.getEventStartDate());
            eventEntity.setEventEndDate(eventDto.getEventEndDate());
            eventRepository.save(eventEntity);
            return eventMapper.toDto(eventEntity);
        }
    }
}
