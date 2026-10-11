package com.college.eventreg.service;

import com.college.eventreg.dto.RegistrationRequestDto;
import com.college.eventreg.model.Event;
import com.college.eventreg.model.Registration;
import com.college.eventreg.model.RegistrationStatus;
import com.college.eventreg.repository.EventRepository;
import com.college.eventreg.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;

@Service
public class EventRegistrationService {

    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;
    private static final String CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public EventRegistrationService(EventRepository eventRepository, RegistrationRepository registrationRepository) {
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Transactional
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Transactional
    public Registration registerStudent(RegistrationRequestDto dto) {
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Selected event not found with ID: " + dto.getEventId()));

        if (event.isFull()) {
            throw new IllegalStateException("Event '" + event.getTitle() + "' has reached its maximum capacity of " + event.getMaxCapacity() + " participants.");
        }

        boolean alreadyRegistered = registrationRepository.existsByEventIdAndStudentRollNumberIgnoreCase(
                event.getId(), dto.getStudentRollNumber().trim()
        );

        if (alreadyRegistered) {
            throw new IllegalArgumentException("Student with Roll Number " + dto.getStudentRollNumber().trim() + " is already registered for this event.");
        }

        String registrationCode = generateUniqueCode();
        Registration registration = new Registration(
                registrationCode,
                event,
                dto.getStudentName().trim(),
                dto.getStudentRollNumber().trim().toUpperCase(),
                dto.getStudentEmail().trim().toLowerCase(),
                dto.getStudentDepartment().trim(),
                dto.getStudentPhone().trim()
        );

        Registration savedRegistration = registrationRepository.save(registration);
        event.incrementRegistrations();
        eventRepository.save(event);

        return savedRegistration;
    }

    public Optional<Registration> findByRegistrationCode(String code) {
        if (code == null) return Optional.empty();
        return registrationRepository.findByRegistrationCode(code.trim().toUpperCase());
    }

    public List<Registration> findByRollNumber(String rollNumber) {
        if (rollNumber == null) return Collections.emptyList();
        return registrationRepository.findByStudentRollNumberIgnoreCase(rollNumber.trim());
    }

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public List<Registration> getRegistrationsByEvent(Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    public List<Registration> getRegistrationsByStatus(RegistrationStatus status) {
        return registrationRepository.findByStatus(status);
    }

    @Transactional
    public Registration updateStatus(Long registrationId, RegistrationStatus newStatus, String remarks) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration record not found with ID: " + registrationId));

        RegistrationStatus oldStatus = registration.getStatus();
        registration.setStatus(newStatus);
        if (remarks != null && !remarks.isBlank()) {
            registration.setAdminRemarks(remarks.trim());
        }

        // Adjust event capacity if status changes to REJECTED from PENDING or APPROVED
        Event event = registration.getEvent();
        if (newStatus == RegistrationStatus.REJECTED && oldStatus != RegistrationStatus.REJECTED) {
            event.decrementRegistrations();
            eventRepository.save(event);
        } else if (oldStatus == RegistrationStatus.REJECTED && newStatus != RegistrationStatus.REJECTED) {
            event.incrementRegistrations();
            eventRepository.save(event);
        }

        return registrationRepository.save(registration);
    }

    public Map<String, Object> getMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        long totalEvents = eventRepository.count();
        long totalRegistrations = registrationRepository.count();
        long pending = registrationRepository.countByStatus(RegistrationStatus.PENDING);
        long approved = registrationRepository.countByStatus(RegistrationStatus.APPROVED);
        long rejected = registrationRepository.countByStatus(RegistrationStatus.REJECTED);

        metrics.put("totalEvents", totalEvents);
        metrics.put("totalRegistrations", totalRegistrations);
        metrics.put("pending", pending);
        metrics.put("approved", approved);
        metrics.put("rejected", rejected);
        return metrics;
    }

    private String generateUniqueCode() {
        int year = LocalDate.now().getYear();
        String code;
        do {
            StringBuilder sb = new StringBuilder("REG-").append(year).append("-");
            for (int i = 0; i < 5; i++) {
                sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
            }
            code = sb.toString();
        } while (registrationRepository.findByRegistrationCode(code).isPresent());

        return code;
    }
}
