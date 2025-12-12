package com.example.ConflictTracker.service;

import com.example.ConflictTracker.dto.ConflictDto;
import com.example.ConflictTracker.mapper.ConflictMapper;
import com.example.ConflictTracker.model.Conflict;
import com.example.ConflictTracker.model.ConflictStatus;
import com.example.ConflictTracker.repository.ConflictRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConflictService {

    private final ConflictRepository repository;
    private final ConflictMapper mapper;

    public ConflictService(ConflictRepository repository, ConflictMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ConflictDto> getAllConflicts() {
        List<Conflict> conflicts = new ArrayList<>();
        repository.findAll().forEach(conflicts::add);
        return mapper.toDto(conflicts);
    }

    public ConflictDto getConflictById(Long id) {
        Conflict conflict = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conflict not found"));
        return mapper.toDto(conflict);
    }

    public ConflictDto addConflict(ConflictDto dto) {
        Conflict conflict = mapper.toEntity(dto);
        repository.save(conflict);
        return mapper.toDto(conflict);
    }


    public List<ConflictDto> findByStatus(ConflictStatus status) {
        return mapper.toDto(repository.findByStatus(status));
    }

    public List<ConflictDto> getConflictsByCountry(String code) {
        return mapper.toDto(repository.findByCountriesCode(code));
    }

    public ConflictDto updateConflict(Long id, ConflictDto dto) {
        Conflict conflict = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conflict not found"));

        conflict.setName(dto.name());
        conflict.setStartDate(dto.startDate());
        conflict.setDescription(dto.description());

        if (dto.status() != null) {
            conflict.setStatus(ConflictStatus.valueOf(dto.status()));
        }

        repository.save(conflict);
        return mapper.toDto(conflict);
    }

    // ===== DELETE =====
    public void deleteConflict(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Conflict not found");
        }
        repository.deleteById(id);
    }

}
