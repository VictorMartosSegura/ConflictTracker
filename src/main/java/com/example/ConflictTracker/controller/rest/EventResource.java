package com.example.ConflictTracker.controller.rest;

import com.example.ConflictTracker.dto.EventDto;
import com.example.ConflictTracker.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EventResource.EVENTS)
public class EventResource {

    public static final String EVENTS = "/api/v1/events";

    private final EventService service;

    public EventResource(EventService service) {
        this.service = service;
    }

    // ===== GET ALL =====
    // GET /api/v1/events
    @GetMapping
    public List<EventDto> getEvents() {
        return service.getAllEvents();
    }

    // ===== GET BY ID =====
    // GET /api/v1/events/{id}
    @GetMapping("/{id}")
    public EventDto getEvent(@PathVariable Long id) {
        return service.getEventById(id);
    }

    // ===== POST =====
    // POST /api/v1/events
    @PostMapping
    public EventDto createEvent(@RequestBody EventDto dto) {
        return service.addEvent(dto);
    }

    // ===== PUT =====
    // PUT /api/v1/events/{id}
    @PutMapping("/{id}")
    public EventDto updateEvent(
            @PathVariable Long id,
            @RequestBody EventDto dto) {
        return service.updateEvent(id, dto);
    }

    // ===== DELETE =====
    // DELETE /api/v1/events/{id}
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        service.deleteEvent(id);
    }
}
