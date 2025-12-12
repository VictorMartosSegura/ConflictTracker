package com.example.ConflictTracker.service;

import com.example.ConflictTracker.dto.EventDto;
import com.example.ConflictTracker.mapper.EventMapper;
import com.example.ConflictTracker.model.Event;
import com.example.ConflictTracker.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper mapper;

    public EventService(EventRepository eventRepository, EventMapper mapper) {
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }

    // ===== GET ALL =====
    public List<EventDto> getAllEvents() {
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        return mapper.toDto(events);
    }

    // ===== GET BY ID =====
    public EventDto getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return mapper.toDto(event);
    }

    // ===== POST =====
    public EventDto addEvent(EventDto dto) {
        Event event = mapper.toEntity(dto);
        eventRepository.save(event);
        return mapper.toDto(event);
    }

    // ===== PUT =====
    public EventDto updateEvent(Long id, EventDto dto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setEventDate(dto.eventDate());
        event.setLocation(dto.location());
        event.setDescription(dto.description());

        eventRepository.save(event);
        return mapper.toDto(event);
    }

    // ===== DELETE =====
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found");
        }
        eventRepository.deleteById(id);
    }
}
