# Milestone 2: Agile Planning and DevOps Workflow

**Student:** 23102A0003 — SIDDHESH RAJGURE  
**Project Title:** Jenkins-Based College Event Registration Platform

---

## 1. User Stories & Acceptance Criteria (Gherkin Format)

### User Story 1: Event Registration
- **As a** College Student,
- **I want to** submit my name, roll number, department, and contact details for a selected event,
- **So that** I can reserve a seat and receive an immediate registration code.
- **Acceptance Criteria:**
  - *Given* I am on the `/register` page and select an open event,
  - *When* I fill in all mandatory fields with valid formats and click submit,
  - *Then* I am redirected to `/confirmation` showing my unique `REG-2026-XXXXX` code and event details.

### User Story 2: Form Validation & Duplicate Prevention
- **As a** System Auditor,
- **I want to** reject invalid student inputs and prevent duplicate registrations by roll number,
- **So that** event capacity is protected from spam or accidental double submissions.
- **Acceptance Criteria:**
  - *Given* a student attempts to submit an empty form or invalid email/phone,
  - *When* they submit the form,
  - *Then* the form highlights the exact erroneous fields and does not persist data.
  - *Given* a student with roll number `23102A0003` is already registered for Hackathon 2026,
  - *When* another registration is attempted with `23102A0003` for the same event,
  - *Then* the application displays an error message: "Student with Roll Number 23102A0003 is already registered for this event."

### User Story 3: Participant Status Tracking
- **As a** Registered Student,
- **I want to** query my registration status using my registration code or roll number,
- **So that** I know whether my participation has been confirmed or is pending review.
- **Acceptance Criteria:**
  - *Given* I visit `/status` and enter `23102A0003`,
  - *When* I click "Track Status",
  - *Then* the system renders my registration record, current status (`APPROVED`/`PENDING`), and coordinator remarks.

### User Story 4: Admin Moderation & Capacity Management
- **As an** Event Coordinator,
- **I want to** view registered participants and approve or reject submissions in real time,
- **So that** event seat quotas and student eligibility are accurately moderated.
- **Acceptance Criteria:**
  - *Given* an organizer is on `/admin`,
  - *When* they click "Approve" on a pending entry,
  - *Then* the participant status transitions to `APPROVED` and remarks are recorded.
  - *When* they click "Reject",
  - *Then* the status transitions to `REJECTED` and the event registration count is decremented.

---

## 2. 15-Task Kanban Sprint Board

| Backlog | In Progress | In Testing | Done (Verified in CI) |
|---|---|---|---|
| • Role-based OAuth (Future)<br>• SMS notification gateway | • Multi-cluster Kubernetes helm chart | • Ansible Idempotency verification<br>• Rollback playbook testing | • 1. Maven build setup<br>• 2. JPA models & H2 DB<br>• 3. Event catalog page<br>• 4. Registration UI<br>• 5. Input validation<br>• 6. Pass code generator<br>• 7. Status tracking<br>• 8. Admin console<br>• 9. Moderation filters<br>• 10. JUnit 5 test suite<br>• 11. Git branching/PRs<br>• 12. Jenkinsfile CI pipeline<br>• 13. Selenium E2E suite<br>• 14. Docker multi-stage<br>• 15. Ansible playbooks |

---

## 3. Definition of Done (DoD)
A user story or task is marked **DONE** only when:
1. Code follows clean architecture and passes static analysis without syntax warnings.
2. 100% of unit tests pass with zero regressions (`mvn test`).
3. Automated Selenium WebDriver journeys execute successfully in headless mode.
4. Jenkins CI pipeline successfully builds, archives the JAR artifact, and records passing JUnit test reports.
5. Docker image builds cleanly with non-root security principles.
6. Documentation and release notes are updated.

---

## 4. End-to-End DevOps Lifecycle Workflow

```mermaid
flowchart LR
    subgraph Plan_and_Code ["1. Plan & Code"]
        A[Git Checkout feature branch] --> B[Implement Core MVC / DTO]
        B --> C[Local Unit Tests mvn test]
        C --> D[Git Commit & Push PR]
    end

    subgraph CI_Quality_Gate ["2. Jenkins CI Automation"]
        D --> E[Jenkins SCM Polling / Trigger]
        E --> F[Compile & Package]
        F --> G[Run Unit & Web Tests]
        G --> H{Selenium E2E Pass?}
        H -- No --> H_Fail[Halt Pipeline & Alert]
        H -- Yes --> I[Archive JAR Artifact]
    end

    subgraph Container_and_Deploy ["3. Delivery & Operations"]
        I --> J[Build Docker Image v1.0.0]
        J --> K[Ansible Target Node Setup]
        K --> L[Deploy Container & Verify Health]
        L --> M{Health Probe 200 OK?}
        M -- No --> M_Rollback[Ansible Auto-Rollback]
        M -- Yes --> N[Live Production Service :8080]
    end
```
