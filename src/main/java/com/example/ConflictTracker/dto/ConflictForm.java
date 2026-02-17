package com.example.ConflictTracker.dto;

import com.example.ConflictTracker.model.ConflictStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ConflictForm {

    @NotBlank(message = "El nom és obligatori")
    @Size(min = 2, max = 100, message = "El nom ha de tenir entre 2 i 100 caràcters")
    private String name;

    @NotNull(message = "La data d'inici és obligatòria")
    private LocalDate startDate;

    @NotNull(message = "L'estat és obligatori")
    private ConflictStatus status;

    @NotBlank(message = "La descripció és obligatòria")
    @Size(min = 10, max = 2000, message = "La descripció ha de tenir entre 10 i 2000 caràcters")
    private String description;

    // Getters/Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public ConflictStatus getStatus() { return status; }
    public void setStatus(ConflictStatus status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
