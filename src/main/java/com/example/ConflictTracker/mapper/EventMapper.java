package com.example.ConflictTracker.mapper;

import com.example.ConflictTracker.dto.EventDto;
import com.example.ConflictTracker.model.Event;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventDto toDto(Event event);

    List<EventDto> toDto(List<Event> events);

    Event toEntity(EventDto dto);
}
