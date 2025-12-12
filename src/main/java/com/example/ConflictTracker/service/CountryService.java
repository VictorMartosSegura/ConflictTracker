package com.example.ConflictTracker.service;

import com.example.ConflictTracker.dto.ConflictDto;
import com.example.ConflictTracker.dto.CountryDto;
import com.example.ConflictTracker.mapper.ConflictMapper;
import com.example.ConflictTracker.mapper.CountryMapper;
import com.example.ConflictTracker.model.Country;
import com.example.ConflictTracker.repository.ConflictRepository;
import com.example.ConflictTracker.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {



    private final CountryRepository repository;
    private final ConflictRepository conflictRepository;
    private final CountryMapper countryMapper;
    private final ConflictMapper conflictMapper;

    public CountryService(CountryRepository repository, ConflictRepository conflictRepository, CountryMapper mapper, ConflictMapper conflictMapper) {
        this.repository = repository;
        this.conflictRepository = conflictRepository;
        this.countryMapper = mapper;
        this.conflictMapper = conflictMapper;
    }

    public List<CountryDto> getAllCountries() {
        List<Country> countries = new ArrayList<>();
        repository.findAll().forEach(countries::add);
        return countryMapper.toDto(countries);
    }

    public CountryDto getCountryById(Long id) {
        Country c = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));
        return countryMapper.toDto(c);
    }

    public CountryDto addCountry(CountryDto dto) {
        Country c = countryMapper.toEntity(dto);
        repository.save(c);
        return countryMapper.toDto(c);
    }

    public CountryDto findByCode(String code) {
        Country c = repository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Country code not found"));
        return countryMapper.toDto(c);
    }

    public List<ConflictDto> getConflictsByCountry(String code) {
        return conflictMapper.toDto(
                conflictRepository.findByCountriesCode(code)
        );
    }

}
