package com.college.eventreg.controller;

import com.college.eventreg.dto.ApprovalRequestDto;
import com.college.eventreg.model.Registration;
import com.college.eventreg.service.EventRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminApiController {

    private final EventRegistrationService registrationService;

    public AdminApiController(EventRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/registrations")
    public ResponseEntity<List<Registration>> getAllRegistrations() {
        return ResponseEntity.ok(registrationService.getAllRegistrations());
    }

    @PatchMapping("/registrations/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody ApprovalRequestDto request) {
        try {
            Registration updated = registrationService.updateStatus(id, request.getStatus(), request.getAdminRemarks());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Not Found");
            error.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
