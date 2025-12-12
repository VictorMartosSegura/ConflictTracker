package com.example.ConflictTracker.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "faction")
public class Faction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "conflict_id")
    private Conflict conflict;

    @ManyToMany
    @JoinTable(
            name = "faction_country",
            joinColumns = @JoinColumn(name = "faction_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<Country> supporters = new HashSet<>();

    // Getters i setters


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Conflict getConflict() {
        return conflict;
    }

    public Set<Country> getSupporters() {
        return supporters;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConflict(Conflict conflict) {
        this.conflict = conflict;
    }
}
