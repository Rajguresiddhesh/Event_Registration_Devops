package com.college.eventreg.controller;

import com.college.eventreg.model.Event;
import com.college.eventreg.model.Registration;
import com.college.eventreg.service.EventRegistrationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRegistrationService registrationService;

    @Test
    @DisplayName("Integration Test: Landing Page Loads Events and Student Information")
    void testLandingPageLoads() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("CampusEvents")))
                .andExpect(content().string(containsString("23102A0003")))
                .andExpect(content().string(containsString("SIDDHESH RAJGURE")));
    }

    @Test
    @DisplayName("Integration Test: REST API /api/events Returns JSON Event List")
    void testGetEventsApi() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[0].category").exists());
    }

    @Test
    @DisplayName("Integration Test: Form Submission Validation Failure on Empty Input")
    void testFormValidationFailure() throws Exception {
        mockMvc.perform(post("/register")
                        .param("studentName", "")
                        .param("studentRollNumber", "")
                        .param("studentEmail", "invalid-email"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().hasErrors());
    }

    @Test
    @DisplayName("Integration Test: Status Tracker Finds Seed Student Record")
    void testStatusTrackerLookup() throws Exception {
        mockMvc.perform(get("/status").param("query", "23102A0003"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("23102A0003")))
                .andExpect(content().string(containsString("SIDDHESH RAJGURE")));
    }
}
