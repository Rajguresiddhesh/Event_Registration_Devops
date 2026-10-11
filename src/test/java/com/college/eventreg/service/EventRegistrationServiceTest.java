package com.college.eventreg.service;

import com.college.eventreg.dto.RegistrationRequestDto;
import com.college.eventreg.model.Event;
import com.college.eventreg.model.Registration;
import com.college.eventreg.model.RegistrationStatus;
import com.college.eventreg.repository.EventRepository;
import com.college.eventreg.repository.RegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventRegistrationServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private EventRegistrationService service;

    private Event testEvent;

    @BeforeEach
    void setUp() {
        testEvent = new Event("DevOps Hackathon 2026", "Technical", "Test Description", "2026-11-15", "Lab 1", 50, 0.0);
        testEvent.setId(10L);
    }

    @Test
    @DisplayName("Unit Test: Student Registration Success with Valid Data")
    void testRegisterStudentSuccess() {
        RegistrationRequestDto dto = new RegistrationRequestDto(
                10L, "Siddhesh Rajgure", "23102A0003", "siddhesh@college.edu", "CSE", "+91 9876543210"
        );

        when(eventRepository.findById(10L)).thenReturn(Optional.of(testEvent));
        when(registrationRepository.existsByEventIdAndStudentRollNumberIgnoreCase(10L, "23102A0003")).thenReturn(false);
        when(registrationRepository.findByRegistrationCode(anyString())).thenReturn(Optional.empty());
        when(registrationRepository.save(any(Registration.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Registration result = service.registerStudent(dto);

        assertNotNull(result);
        assertTrue(result.getRegistrationCode().startsWith("REG-2026-"));
        assertEquals("23102A0003", result.getStudentRollNumber());
        assertEquals("Siddhesh Rajgure", result.getStudentName());
        assertEquals(RegistrationStatus.PENDING, result.getStatus());
        assertEquals(1, testEvent.getCurrentRegistrations());
        verify(eventRepository, times(1)).save(testEvent);
    }

    @Test
    @DisplayName("Unit Test: Duplicate Registration Prevention for Same Roll Number")
    void testDuplicateRegistrationFails() {
        RegistrationRequestDto dto = new RegistrationRequestDto(
                10L, "Siddhesh Rajgure", "23102A0003", "siddhesh@college.edu", "CSE", "+91 9876543210"
        );

        when(eventRepository.findById(10L)).thenReturn(Optional.of(testEvent));
        when(registrationRepository.existsByEventIdAndStudentRollNumberIgnoreCase(10L, "23102A0003")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.registerStudent(dto));
        assertTrue(ex.getMessage().contains("already registered"));
        verify(registrationRepository, never()).save(any());
    }

    @Test
    @DisplayName("Unit Test: Event Maximum Capacity Limit Rejection")
    void testEventCapacityExceededFails() {
        testEvent.setCurrentRegistrations(50); // Capacity is 50
        RegistrationRequestDto dto = new RegistrationRequestDto(
                10L, "New Student", "23102A9999", "new@college.edu", "IT", "+91 9999999999"
        );

        when(eventRepository.findById(10L)).thenReturn(Optional.of(testEvent));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> service.registerStudent(dto));
        assertTrue(ex.getMessage().contains("maximum capacity"));
        verify(registrationRepository, never()).save(any());
    }

    @Test
    @DisplayName("Unit Test: Organizer Approval Workflow")
    void testUpdateRegistrationStatus() {
        Registration reg = new Registration(
                "REG-2026-TEST1", testEvent, "Siddhesh Rajgure", "23102A0003", "siddhesh@college.edu", "CSE", "+91 9876543210"
        );
        reg.setId(100L);

        when(registrationRepository.findById(100L)).thenReturn(Optional.of(reg));
        when(registrationRepository.save(any(Registration.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Registration updated = service.updateStatus(100L, RegistrationStatus.APPROVED, "Verified attendance credentials");

        assertEquals(RegistrationStatus.APPROVED, updated.getStatus());
        assertEquals("Verified attendance credentials", updated.getAdminRemarks());
        verify(registrationRepository, times(1)).save(reg);
    }
}
