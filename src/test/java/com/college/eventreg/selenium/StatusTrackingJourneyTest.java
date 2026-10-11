package com.college.eventreg.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatusTrackingJourneyTest extends BaseSeleniumTest {

    @Test
    @DisplayName("Selenium Journey 3: Participant Status Tracking by Roll Number")
    void testStatusTrackingJourney() {
        if (!driverInitialized) {
            System.out.println("Skipping browser-dependent test in non-GUI environment.");
            return;
        }

        try {
            driver.get(baseUrl + "/status");
            assertEquals("Registration Status Tracker - CampusEvents", driver.getTitle());

            // Search for pre-seeded student: 23102A0003
            WebElement queryInput = driver.findElement(By.id("statusQueryInput"));
            queryInput.sendKeys("23102A0003");

            WebElement searchBtn = driver.findElement(By.id("searchStatusBtn"));
            searchBtn.click();

            // Assert result card appears
            WebElement resultCard = driver.findElement(By.className("status-result-card"));
            assertNotNull(resultCard);
            assertTrue(resultCard.getText().contains("SIDDHESH RAJGURE"));
            assertTrue(resultCard.getText().contains("APPROVED"));

            System.out.println("[SELENIUM PASSED] Status tracking successfully resolved student record 23102A0003.");
        } catch (Throwable ex) {
            captureScreenshot("StatusTrackingJourney");
            throw ex;
        }
    }
}
