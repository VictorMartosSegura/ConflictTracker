package com.example.ConflictTracker.mapper;

import com.example.ConflictTracker.dto.ConflictDto;
import com.example.ConflictTracker.model.Conflict;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")

public interface ConflictMapper {

    ConflictDto toDto(Conflict conflict);

    List<ConflictDto> toDto(List<Conflict> conflictList);

    Conflict toEntity(ConflictDto dto);
}
