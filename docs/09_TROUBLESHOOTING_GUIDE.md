# Technical Troubleshooting Guide

**Student:** 23102A0003 — SIDDHESH RAJGURE  
**Project Title:** Jenkins-Based College Event Registration Platform

---

## 1. Port 8080 Conflict (`Address already in use`)
- **Symptom:** `Web server failed to start. Port 8080 was already in use.`
- **Resolution:**
  - *Option A (Windows PowerShell):* Find and terminate process occupying port 8080:
    ```powershell
    Get-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess | Stop-Process -Force
    ```
  - *Option B (Override Port):* Launch on an alternative port via environment variable:
    ```bash
    PORT=9090 ./mvnw spring-boot:run
    ```

---

## 2. Maven Wrapper or `mvn` Command Not Recognized
- **Symptom:** `mvn : The term 'mvn' is not recognized...`
- **Resolution:**
  - Execute using the embedded Maven wrapper script:
    - On Windows: `.\mvnw.cmd test`
    - On Linux/macOS: `./mvnw test`
  - Ensure `JAVA_HOME` points to JDK 17, 21, or 22.

---

## 3. Selenium Headless Browser / CDP Driver Warnings
- **Symptom:** `Unable to find CDP implementation matching 154` or `SessionNotCreatedException`
- **Explanation & Fix:**
  - The Chrome DevTools Protocol (CDP) warning is informational and does not affect Selenium DOM actions.
  - On systems without physical Chrome or Edge installed, `BaseSeleniumTest` detects missing binaries and gracefully skips browser execution while allowing unit and MockMvc tests to validate business logic.
  - To install matching driver automatically: `io.github.bonigarcia:webdrivermanager` handles binary downloads in headless Linux containers.

---

## 4. Jenkins Quality Gate Halts Deployment
- **Symptom:** Jenkins build turns RED at Stage 4 (Selenium) or Stage 3 (Unit Tests).
- **Resolution:**
  1. Inspect test report: Navigate to **Jenkins Job -> Build # -> Test Result**.
  2. Inspect failure screenshots archived in `target/selenium-screenshots/`.
  3. Correct the regression in your feature branch, run `./mvnw test` locally to verify 100% pass rate, then push commit to rerun Jenkins.

---

## 5. Docker Container Exits Immediately (`Exit Code 1`)
- **Symptom:** `docker ps` shows container has stopped.
- **Resolution:**
  - Inspect logs: `docker logs college-event-platform`
  - Verify Spring Boot Actuator health endpoint: ensure container memory is not constrained. Standard heap flag `-XX:MaxRAMPercentage=75.0` allows JVM to dynamically scale with container cgroup limits.

---

## 6. Ansible `Permission denied (publickey)`
- **Symptom:** Ansible playbook fails to connect to remote target node.
- **Resolution:**
  - Verify private key permissions: `chmod 600 ~/.ssh/id_rsa`
  - Test ad-hoc connectivity: `ansible app_servers -i ansible/inventory.ini -m ping`
  - When testing locally on a single machine, ensure `ansible_connection=local` is set in `inventory.ini`.
