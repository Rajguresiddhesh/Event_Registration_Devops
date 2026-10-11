package com.college.eventreg.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationJourneyTest extends BaseSeleniumTest {

    @Test
    @DisplayName("Selenium Journey 1: Complete Student Registration & Confirmation Badge Issuance")
    void testCompleteRegistrationJourney() {
        if (!driverInitialized) {
            System.out.println("Skipping browser-dependent test in non-GUI environment.");
            return;
        }

        try {
            // 1. Visit Register Page
            driver.get(baseUrl + "/register");
            assertEquals("Event Registration - CampusEvents", driver.getTitle());

            // 2. Select Event
            WebElement eventSelect = driver.findElement(By.id("eventId"));
            Select select = new Select(eventSelect);
            select.selectByIndex(1);

            // 3. Fill Student Information
            driver.findElement(By.id("studentName")).sendKeys("Ananya Sharma");
            driver.findElement(By.id("studentRollNumber")).sendKeys("23102A0055");
            driver.findElement(By.id("studentEmail")).sendKeys("ananya.sharma@college.edu");

            WebElement deptSelect = driver.findElement(By.id("studentDepartment"));
            new Select(deptSelect).selectByVisibleText("Information Technology");

            driver.findElement(By.id("studentPhone")).sendKeys("+91 9123456789");
            driver.findElement(By.id("terms")).click();

            // 4. Submit Registration Form
            driver.findElement(By.id("submitRegBtn")).click();

            // 5. Assert Confirmation Page Loaded
            assertTrue(driver.getCurrentUrl().contains("/confirmation"));
            WebElement codeEl = driver.findElement(By.id("confirmationCode"));
            assertNotNull(codeEl);
            assertTrue(codeEl.getText().startsWith("REG-2026-"));
            System.out.println("[SELENIUM PASSED] Student successfully registered with code: " + codeEl.getText());

        } catch (Throwable ex) {
            captureScreenshot("CompleteRegistrationJourney");
            throw ex;
        }
    }
}
