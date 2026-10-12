package com.college.eventreg.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public abstract class BaseSeleniumTest {

    @LocalServerPort
    protected int port;

    protected WebDriver driver;
    protected String baseUrl;
    protected boolean driverInitialized = false;

    @BeforeEach
    void setUpDriver() {
        baseUrl = "http://localhost:" + port;

        try {
            // Attempt Headless Chrome first
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("--window-size=1920,1080");
            Path chromiumBinary = Paths.get("/usr/bin/chromium");
            if (Files.isExecutable(chromiumBinary)) {
                chromeOptions.setBinary(chromiumBinary.toString());
            }

            driver = new ChromeDriver(chromeOptions);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driverInitialized = true;
        } catch (Throwable chromeEx) {
            try {
                // Fallback to Headless Edge on Windows
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--headless=new");
                edgeOptions.addArguments("--disable-gpu");
                edgeOptions.addArguments("--no-sandbox");
                driver = new EdgeDriver(edgeOptions);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                driverInitialized = true;
            } catch (Throwable edgeEx) {
                if (Boolean.getBoolean("selenium.required")) {
                    IllegalStateException failure = new IllegalStateException(
                            "A Chrome or Edge browser is required, but neither headless WebDriver could start.",
                            edgeEx);
                    failure.addSuppressed(chromeEx);
                    throw failure;
                }
                System.out.println("[SELENIUM NOTICE] No physical Chrome/Edge binary available in current headless environment: " + edgeEx.getMessage());
                driverInitialized = false;
            }
        }
    }

    protected void captureScreenshot(String testName) {
        if (driver != null && driver instanceof TakesScreenshot) {
            try {
                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Path outputDir = Paths.get("target", "selenium-screenshots");
                Files.createDirectories(outputDir);
                File screenshotFile = outputDir.resolve("failure-" + testName + "-" + System.currentTimeMillis() + ".png").toFile();
                try (FileOutputStream fos = new FileOutputStream(screenshotFile)) {
                    fos.write(screenshotBytes);
                }
                System.out.println("[SELENIUM SCREENSHOT CAPTURED] " + screenshotFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to save screenshot: " + e.getMessage());
            }
        }
    }

    @AfterEach
    void tearDownDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {
            }
        }
    }
}
