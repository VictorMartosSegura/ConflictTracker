package com.example.ConflictTracker.controller.web;

import com.example.ConflictTracker.dto.ConflictDto;
import com.example.ConflictTracker.dto.ConflictForm;
import com.example.ConflictTracker.model.ConflictStatus;
import com.example.ConflictTracker.service.ConflictService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web/conflicts")
public class ConflictControllerWeb {

    @Autowired
    private ConflictService service;

    // ===== LISTADO =====
    @GetMapping
    public String llistaConflictes(Model model) {
        List<ConflictDto> conflicts = service.getAllConflicts();
        model.addAttribute("llistaConflictes", conflicts);
        return "llista-conflictes";
    }

    // ===== FORMULARIO =====
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("conflictForm", new ConflictForm());
        model.addAttribute("statuses", ConflictStatus.values());
        return "conflict-form";
    }

    // ===== GUARDAR =====
    @PostMapping
    public String createConflict(
            @Valid @ModelAttribute("conflictForm") ConflictForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", ConflictStatus.values());
            return "conflict-form";
        }

        ConflictDto dto = new ConflictDto(
                null,
                form.getName(),
                form.getStartDate(),
                form.getStatus().name(),
                form.getDescription()
        );

        service.addConflict(dto);

        return "redirect:/web/conflicts";
    }
}
