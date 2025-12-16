package com.example.ConflictTracker.controller.rest;

import com.example.ConflictTracker.dto.ConflictDto;
import com.example.ConflictTracker.dto.CountryDto;
import com.example.ConflictTracker.service.ConflictService;
import com.example.ConflictTracker.service.CountryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CountryResource.COUNTRIES)
public class CountryResource {

    public static final String COUNTRIES = "/api/v1/countries";

    private final CountryService countryService;
    private final ConflictService conflictService;

    public CountryResource(CountryService service, ConflictService conflictServiceservice) {
        this.countryService = service;
        this.conflictService = conflictServiceservice;
    }

    @GetMapping
    public List<CountryDto> getCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/{id}")
    public CountryDto getCountry(@PathVariable Long id) {
        return countryService.getCountryById(id);
    }

    @PostMapping
    public CountryDto createCountry(@RequestBody CountryDto dto) {
        return countryService.addCountry(dto);
    }

    @GetMapping("/code/{code}")
    public CountryDto getByCode(@PathVariable String code) {
        return countryService.findByCode(code);
    }

    @GetMapping("/{code}/conflicts")
    public List<ConflictDto> getConflictsByCountry(@PathVariable String code) {
        return conflictService.getConflictsByCountry(code);
    }

    @PutMapping("/{id}")
    public CountryDto updateCountry(
            @PathVariable Long id,
            @RequestBody CountryDto dto) {
        return countryService.updateCountry(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
    }


}
