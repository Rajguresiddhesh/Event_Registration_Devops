package com.college.eventreg.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidationJourneyTest extends BaseSeleniumTest {

    @Test
    @DisplayName("Selenium Journey 2: Form Input Validation Rejection on Missing Required Fields")
    void testFormValidationJourney() {
        if (!driverInitialized) {
            System.out.println("Skipping browser-dependent test in non-GUI environment.");
            return;
        }

        try {
            driver.get(baseUrl + "/register");

            // Attempt to submit empty form
            WebElement submitBtn = driver.findElement(By.id("submitRegBtn"));
            submitBtn.click();

            // Assert page remains on /register due to HTML5 & server-side validation
            assertTrue(driver.getCurrentUrl().contains("/register"));

            // Check that required inputs exist
            WebElement nameField = driver.findElement(By.id("studentName"));
            assertEquals("true", nameField.getAttribute("required"));

            System.out.println("[SELENIUM PASSED] Form validation successfully blocked invalid blank submission.");
        } catch (Throwable ex) {
            captureScreenshot("FormValidationJourney");
            throw ex;
        }
    }
}
