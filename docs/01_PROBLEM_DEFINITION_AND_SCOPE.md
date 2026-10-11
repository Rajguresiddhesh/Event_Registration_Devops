# Milestone 1: Problem Definition and Scope

**Student:** 23102A0003 — SIDDHESH RAJGURE  
**Project Title:** Jenkins-Based College Event Registration Platform

---

## 1. Real-Time Need Analysis
Modern educational institutions host numerous technical symposiums, hackathons, guest seminars, robotics competitions, and cultural festivals annually. Currently, event registration workflows suffer from significant operational bottlenecks:
- **Disparate Google Forms / Paper Submissions:** High error rates, unverified student credentials, and lack of real-time seat quota control.
- **Absence of Immediate Confirmation:** Students lack instant confirmation codes or digital event passes, causing confusion on event day.
- **Opaque Moderation Workflow:** Event coordinators spend hours reconciling spreadsheets manually without audit logs or real-time status tracking.
- **Manual, Slow Deployment:** Technical committees struggle with manual deployments, unversioned server files, and runtime downtime during traffic spikes.

---

## 2. Target Users & Stakeholders Matrix

| Stakeholder Group | Primary Role | Core Pain Point Addressed |
|---|---|---|
| **Students / Participants** | Register for events, view ticket status | Eliminates duplicate entries; instant digital pass generation. |
| **Faculty & Event Coordinators** | Review, approve, and reject registrations | Real-time seat allocation, capacity locking, participant rostering. |
| **System Administrators / DevOps Engineers** | Maintain server uptime, automate releases | Continuous Integration (CI), automated testing, and zero-downtime container updates. |
| **College Authorities / Deans** | Review event audit metrics | Accurate attendance figures and institutional data integrity. |

---

## 3. System Constraints
- **Technical Stack:** Java 17/22, Spring Boot 3.3.4, embedded Tomcat, in-memory/persisted H2 database.
- **CI/CD Automation:** Jenkins Declarative Pipeline with multi-stage quality gates and parameterized deployments.
- **Quality Assurance:** Selenium WebDriver automated end-to-end user journeys executed prior to artifact promotion.
- **Portability:** Multi-stage Docker containerization and Ansible configuration management for reproducible environments.
- **Performance:** Sub-200ms page response times, sub-60s full CI build and test execution cycle.

---

## 4. Measurable Success Criteria
1. **Zero Duplicate Registrations:** 100% rejection of duplicate student roll numbers for the same event.
2. **Instant Status Resolution:** Real-time lookup of registration state in < 50ms.
3. **Automated CI Quality Gate:** 100% automated passing of unit, integration, and Selenium tests before binary packaging.
4. **Reproducible Deployments:** Single-command container deployment via Docker and Ansible with zero-downtime health verification.

---

## 5. Frozen 15-Task MVP Scope

```mermaid
flowchart TD
    T1["1. Maven Build & Spring Boot Scaffold"] --> T2["2. JPA Entities & H2 Database Layer"]
    T2 --> T3["3. Event Catalog Display Module"]
    T3 --> T4["4. Student Registration Form UI"]
    T4 --> T5["5. Server-side Validation & DTOs"]
    T5 --> T6["6. Unique Pass Code Generator"]
    T6 --> T7["7. Live Status Tracker Module"]
    T7 --> T8["8. Organizer Admin Console"]
    T8 --> T9["9. Participant Filtering & Moderation"]
    T9 --> T10["10. Unit & Integration Test Suite"]
    T10 --> T11["11. Git Branching & PR Strategy"]
    T11 --> T12["12. Declarative Jenkinsfile Pipeline"]
    T12 --> T13["13. Headless Selenium E2E Suite"]
    T13 --> T14["14. Production Docker Containerization"]
    T14 --> T15["15. Ansible Provisioning & Rollback Playbooks"]
```
