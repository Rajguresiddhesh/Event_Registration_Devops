package com.college.eventreg.controller;

import com.college.eventreg.dto.RegistrationRequestDto;
import com.college.eventreg.model.Event;
import com.college.eventreg.model.Registration;
import com.college.eventreg.service.EventRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RegistrationApiController {

    private final EventRegistrationService registrationService;

    public RegistrationApiController(EventRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(registrationService.getAllEvents());
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        return registrationService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/registrations")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody RegistrationRequestDto dto) {
        try {
            Registration saved = registrationService.registerStudent(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Bad Request");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (IllegalStateException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Capacity Full");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }

    @GetMapping("/registrations/{code}")
    public ResponseEntity<?> getByCode(@PathVariable String code) {
        return registrationService.findByRegistrationCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/registrations/search")
    public ResponseEntity<List<Registration>> searchByRoll(@RequestParam String roll) {
        return ResponseEntity.ok(registrationService.findByRollNumber(roll));
    }

    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Object>> getMetrics() {
        return ResponseEntity.ok(registrationService.getMetrics());
    }
}
