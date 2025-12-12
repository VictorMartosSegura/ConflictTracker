package com.example.ConflictTracker.service;

import com.example.ConflictTracker.dto.FactionDto;
import com.example.ConflictTracker.mapper.FactionMapper;
import com.example.ConflictTracker.model.Conflict;
import com.example.ConflictTracker.model.Country;
import com.example.ConflictTracker.model.Faction;
import com.example.ConflictTracker.repository.ConflictRepository;
import com.example.ConflictTracker.repository.CountryRepository;
import com.example.ConflictTracker.repository.FactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FactionService {

    private final FactionRepository factionRepository;
    private final ConflictRepository conflictRepository;
    private final CountryRepository countryRepository;
    private final FactionMapper mapper;

    public FactionService(FactionRepository factionRepository,
                          ConflictRepository conflictRepository, CountryRepository countryRepository,
                          FactionMapper mapper) {
        this.factionRepository = factionRepository;
        this.conflictRepository = conflictRepository;
        this.countryRepository = countryRepository;
        this.mapper = mapper;
    }

    public List<FactionDto> getAllFactions() {
        List<Faction> factions = new ArrayList<>();
        factionRepository.findAll().forEach(factions::add);
        return mapper.toDto(factions);
    }

    public FactionDto getFactionById(Long id) {
        Faction faction = factionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faction not found"));
        return mapper.toDto(faction);
    }

    public FactionDto addFaction(FactionDto dto) {
        Faction faction = mapper.toEntity(dto);
        factionRepository.save(faction);
        return mapper.toDto(faction);
    }

    public FactionDto addFactionToConflict(Long conflictId, FactionDto dto) {

        Conflict conflict = conflictRepository.findById(conflictId)
                .orElseThrow(() -> new RuntimeException("Conflict not found"));

        Faction faction = mapper.toEntity(dto);
        faction.setConflict(conflict);

        factionRepository.save(faction);
        return mapper.toDto(faction);
    }

    public FactionDto addCountryToFaction(Long factionId, Long countryId) {

        Faction faction = factionRepository.findById(factionId)
                .orElseThrow(() -> new RuntimeException("Faction not found"));

        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country not found"));

        faction.getSupporters().add(country);
        factionRepository.save(faction);

        return mapper.toDto(faction);
    }

    public FactionDto updateFaction(Long id, FactionDto dto) {
        Faction faction = factionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faction not found"));

        faction.setName(dto.name());

        factionRepository.save(faction);
        return mapper.toDto(faction);
    }

    public void deleteFaction(Long id) {
        if (!factionRepository.existsById(id)) {
            throw new RuntimeException("Faction not found");
        }
        factionRepository.deleteById(id);
    }

}
