package com.example.ConflictTracker.controller.rest;

import com.example.ConflictTracker.dto.FactionDto;
import com.example.ConflictTracker.service.FactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(FactionResource.FACTIONS)
public class FactionResource {

    public static final String FACTIONS = "/api/v1/factions";

    private final FactionService service;

    public FactionResource(FactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<FactionDto> getFactions() {
        return service.getAllFactions();
    }

    @GetMapping("/{id}")
    public FactionDto getFaction(@PathVariable Long id) {
        return service.getFactionById(id);
    }

    @PostMapping
    public FactionDto createFaction(@RequestBody FactionDto dto) {
        return service.addFaction(dto);
    }

    @PostMapping("/{factionId}/countries/{countryId}")
    public FactionDto addCountryToFaction(
            @PathVariable Long factionId,
            @PathVariable Long countryId) {

        return service.addCountryToFaction(factionId, countryId);
    }

    @PutMapping("/{id}")
    public FactionDto updateFaction(
            @PathVariable Long id,
            @RequestBody FactionDto dto) {
        return service.updateFaction(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteFaction(@PathVariable Long id) {
        service.deleteFaction(id);
    }

}
