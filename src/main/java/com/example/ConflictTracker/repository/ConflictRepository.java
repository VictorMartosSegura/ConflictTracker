package com.example.ConflictTracker.repository;

import com.example.ConflictTracker.model.Conflict;
import com.example.ConflictTracker.model.ConflictStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConflictRepository extends CrudRepository<Conflict, Long> {

    // GET /conflicts?status=ACTIVE
    List<Conflict> findByStatus(ConflictStatus status);

    // GET /countries/{code}/conflicts
    List<Conflict> findByCountriesCode(String code);
}