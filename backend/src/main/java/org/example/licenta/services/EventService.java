package org.example.licenta.services;

import org.example.licenta.db.entities.EventEntity;
import org.example.licenta.db.repositories.EventRepository;
import org.example.licenta.dto.EventDto;
import org.example.licenta.exceptions.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<EventDto> getEvents() throws EventNotFoundException {
        if (eventRepository.findAll().isEmpty()) {
            throw new EventNotFoundException("No events found");
        }
        else{
            List<EventEntity> events = eventRepository.findAll();
            List<EventDto> eventDtos = new ArrayList<>();
            for (EventEntity event : events) {
                EventDto eventDto = new EventDto();
                eventDto.setEventName(event.getEventName());
                eventDtos.add(eventDto);
            }
            return eventDtos;
        }
    }

    public EventDto getEventById(String id) throws EventNotFoundException {
        Optional<EventEntity> event = eventRepository.findById(Long.valueOf(id));
        if (event.isEmpty()) {
            throw new EventNotFoundException("Event not found");
        }
        else {
            EventDto eventDto = new EventDto();
            eventDto.setEventName(event.get().getEventName());
            return eventDto;
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

    public void createEvent(EventDto eventDto){
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventName(eventDto.getEventName());
        eventRepository.save(eventEntity);
    }

    public EventDto updateEvent(EventDto eventDto, String id) throws EventNotFoundException {
        if (!eventRepository.existsById(Long.valueOf(id))) {
            throw new EventNotFoundException("Event not found");
        }
        else {
            EventEntity eventEntity = eventRepository.findById(Long.valueOf(id)).get();
            eventEntity.setEventName(eventDto.getEventName());
            eventRepository.save(eventEntity);

            EventDto eventDto1 = new EventDto();
            eventDto1.setEventName(eventEntity.getEventName());
            return eventDto1;
        }
    }
}
