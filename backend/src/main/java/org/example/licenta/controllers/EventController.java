package org.example.licenta.controllers;

import org.example.licenta.dto.EventDto;
import org.example.licenta.exceptions.EventAlreadyExistsException;
import org.example.licenta.exceptions.EventNotFoundException;
import org.example.licenta.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/events")
    public List<EventDto> getEvents() throws EventNotFoundException {
        return eventService.getEvents();
    }

    @GetMapping("/events/{id}")
    public EventDto getEventById(@PathVariable String id) throws EventNotFoundException {
        return eventService.getEventById(id);
    }

    @DeleteMapping("/events/{id}/delete")
    public void deleteEvent(@PathVariable String id) throws EventNotFoundException {
        eventService.deleteEvent(id);
    }

    @PostMapping("/events/create")
    public void createEvent(@RequestBody EventDto eventDto) throws EventAlreadyExistsException {
        eventService.createEvent(eventDto);
    }

    @PutMapping("/events/{id}/update")
    public EventDto updateEvent(@RequestBody EventDto eventDto, @PathVariable String id) throws EventNotFoundException {
        return eventService.updateEvent(eventDto, id);
    }
}
