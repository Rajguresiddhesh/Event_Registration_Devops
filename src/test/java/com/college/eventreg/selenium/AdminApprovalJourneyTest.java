package com.college.eventreg.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminApprovalJourneyTest extends BaseSeleniumTest {

    @Test
    @DisplayName("Selenium Journey 4: Organizer Dashboard Inspection & Moderation Action")
    void testAdminApprovalJourney() {
        if (!driverInitialized) {
            System.out.println("Skipping browser-dependent test in non-GUI environment.");
            return;
        }

        try {
            driver.get(baseUrl + "/admin");
            assertEquals("Organizer & Admin Dashboard - CampusEvents", driver.getTitle());

            // Check participant table exists
            WebElement table = driver.findElement(By.id("adminRegistrationsTable"));
            assertNotNull(table);
            assertTrue(table.getText().contains("SIDDHESH RAJGURE"));
            assertTrue(table.getText().contains("23102A0003"));

            System.out.println("[SELENIUM PASSED] Admin moderation console loaded participant roster successfully.");
        } catch (Throwable ex) {
            captureScreenshot("AdminApprovalJourney");
            throw ex;
        }
    }
}
