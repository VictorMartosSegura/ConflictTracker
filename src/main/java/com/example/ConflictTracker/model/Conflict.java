package com.example.ConflictTracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "conflict")
public class Conflict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private ConflictStatus status;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "conflict_country",
            joinColumns = @JoinColumn(name = "conflict_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<Country> countries = new HashSet<>();

    @OneToMany(mappedBy = "conflict", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Faction> factions = new ArrayList<>();

    @OneToMany(mappedBy = "conflict", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    // ===== GETTERS =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ConflictStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    // ===== SETTERS (coherents) =====

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setStatus(ConflictStatus status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }


    public void addCountry(Country country) {
        this.countries.add(country);
    }

    public void removeCountry(Country country) {
        this.countries.remove(country);
    }
}
