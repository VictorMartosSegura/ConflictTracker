package com.example.ConflictTracker.mapper;

import com.example.ConflictTracker.dto.CountryDto;
import com.example.ConflictTracker.model.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryDto toDto(Country country);

    List<CountryDto> toDto(List<Country> countries);

    Country toEntity(CountryDto dto);
}
