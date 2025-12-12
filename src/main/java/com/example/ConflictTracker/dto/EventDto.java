package com.example.ConflictTracker.dto;

import java.time.LocalDate;

public record EventDto(
        Long id,
        LocalDate eventDate,
        String location,
        String description
) {}
