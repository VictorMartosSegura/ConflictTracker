package com.example.ConflictTracker.repository;

import com.example.ConflictTracker.model.Faction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactionRepository extends CrudRepository<Faction, Long> {
}
