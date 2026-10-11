package com.college.eventreg.controller;

import com.college.eventreg.dto.RegistrationRequestDto;
import com.college.eventreg.model.Event;
import com.college.eventreg.model.Registration;
import com.college.eventreg.model.RegistrationStatus;
import com.college.eventreg.service.EventRegistrationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class WebController {

    private final EventRegistrationService registrationService;

    public WebController(EventRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("events", registrationService.getAllEvents());
        model.addAttribute("metrics", registrationService.getMetrics());
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(@RequestParam(value = "eventId", required = false) Long eventId, Model model) {
        RegistrationRequestDto dto = new RegistrationRequestDto();
        if (eventId != null) {
            dto.setEventId(eventId);
        }
        model.addAttribute("registrationDto", dto);
        model.addAttribute("events", registrationService.getAllEvents());
        return "register";
    }

    @PostMapping("/register")
    public String submitRegistration(@Valid @ModelAttribute("registrationDto") RegistrationRequestDto dto,
                                     BindingResult bindingResult,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("events", registrationService.getAllEvents());
            return "register";
        }

        try {
            Registration saved = registrationService.registerStudent(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Registration submitted successfully!");
            return "redirect:/confirmation?code=" + saved.getRegistrationCode();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("events", registrationService.getAllEvents());
            return "register";
        }
    }

    @GetMapping("/confirmation")
    public String showConfirmation(@RequestParam(value = "code", required = false) String code, Model model) {
        if (code != null && !code.isBlank()) {
            Optional<Registration> reg = registrationService.findByRegistrationCode(code);
            reg.ifPresent(registration -> model.addAttribute("registration", registration));
        }
        return "confirmation";
    }

    @GetMapping("/status")
    public String checkStatusPage(@RequestParam(value = "query", required = false) String query, Model model) {
        model.addAttribute("query", query != null ? query : "");
        if (query != null && !query.isBlank()) {
            String trimmed = query.trim();
            // Check by registration code first
            Optional<Registration> byCode = registrationService.findByRegistrationCode(trimmed);
            if (byCode.isPresent()) {
                model.addAttribute("registrations", List.of(byCode.get()));
            } else {
                // Check by roll number
                List<Registration> byRoll = registrationService.findByRollNumber(trimmed);
                model.addAttribute("registrations", byRoll);
                if (byRoll.isEmpty()) {
                    model.addAttribute("noResults", true);
                }
            }
        } else {
            model.addAttribute("registrations", Collections.emptyList());
        }
        return "status";
    }

    @GetMapping("/admin")
    public String adminDashboard(@RequestParam(value = "eventId", required = false) Long eventId,
                                 @RequestParam(value = "status", required = false) RegistrationStatus status,
                                 Model model) {
        List<Registration> list;
        if (eventId != null) {
            list = registrationService.getRegistrationsByEvent(eventId);
        } else if (status != null) {
            list = registrationService.getRegistrationsByStatus(status);
        } else {
            list = registrationService.getAllRegistrations();
        }

        model.addAttribute("registrations", list);
        model.addAttribute("events", registrationService.getAllEvents());
        model.addAttribute("metrics", registrationService.getMetrics());
        model.addAttribute("selectedEventId", eventId);
        model.addAttribute("selectedStatus", status);
        return "admin";
    }

    @PostMapping("/admin/update-status")
    public String updateStatus(@RequestParam("registrationId") Long registrationId,
                               @RequestParam("status") RegistrationStatus status,
                               @RequestParam(value = "remarks", required = false) String remarks,
                               RedirectAttributes redirectAttributes) {
        try {
            registrationService.updateStatus(registrationId, status, remarks);
            redirectAttributes.addFlashAttribute("successMessage", "Registration status updated successfully to " + status);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating status: " + ex.getMessage());
        }
        return "redirect:/admin";
    }
}
