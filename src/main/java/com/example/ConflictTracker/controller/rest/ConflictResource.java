package com.example.ConflictTracker.controller.rest;

import com.example.ConflictTracker.dto.ConflictDto;
import com.example.ConflictTracker.dto.FactionDto;
import com.example.ConflictTracker.model.ConflictStatus;
import com.example.ConflictTracker.service.ConflictService;
import com.example.ConflictTracker.service.FactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ConflictResource.CONFLICTS)
public class ConflictResource {

    public static final String CONFLICTS = "/api/v1/conflicts";

    private final ConflictService conflictService;

    private final FactionService factionService;

    public ConflictResource(ConflictService service, FactionService factionService) {
        this.conflictService = service;
        this.factionService = factionService;
    }

    @GetMapping
    public List<ConflictDto> getConflicts(
            @RequestParam(required = false) ConflictStatus status) {

        if (status != null) return conflictService.findByStatus(status);
        return conflictService.getAllConflicts();
    }

    @GetMapping("/{id}")
    public ConflictDto getConflict(@PathVariable Long id) {
        return conflictService.getConflictById(id);
    }

    @PostMapping
    public ConflictDto createConflict(@RequestBody ConflictDto dto) {
        return conflictService.addConflict(dto);
    }

    @PutMapping("/{id}")
    public ConflictDto updateConflict(
            @PathVariable Long id,
            @RequestBody ConflictDto dto) {
        return conflictService.updateConflict(id, dto);
    }


    @DeleteMapping("/{id}")
    public void deleteConflict(@PathVariable Long id) {
        conflictService.deleteConflict(id);
    }

    @PostMapping("/{conflictId}/factions")
    public FactionDto addFactionToConflict(
            @PathVariable Long conflictId,
            @RequestBody FactionDto factionDto) {
        return factionService.addFactionToConflict(conflictId, factionDto);
    }


}
