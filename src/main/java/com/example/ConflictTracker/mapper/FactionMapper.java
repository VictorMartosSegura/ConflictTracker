package com.example.ConflictTracker.mapper;

import com.example.ConflictTracker.dto.FactionDto;
import com.example.ConflictTracker.model.Faction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FactionMapper {

    FactionDto toDto(Faction faction);

    List<FactionDto> toDto(List<Faction> factions);

    Faction toEntity(FactionDto dto);
}
