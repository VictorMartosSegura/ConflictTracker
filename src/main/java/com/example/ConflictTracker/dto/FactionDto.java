package com.example.ConflictTracker.dto;

import java.util.Set;

public record FactionDto(
        Long id,
        String name,
        Set<CountryDto>supporters
) {}
