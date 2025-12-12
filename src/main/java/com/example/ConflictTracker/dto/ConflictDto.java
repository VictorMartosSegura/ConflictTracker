package com.example.ConflictTracker.dto;

import java.time.LocalDate;

public record ConflictDto(
        Long id,
        String name,
        LocalDate startDate,
        String status,
        String description
) {}
