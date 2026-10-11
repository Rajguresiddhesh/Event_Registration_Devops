# Jenkins-Based College Event Registration Platform

**Candidate Name:** SIDDHESH RAJGURE  
**Student Roll Number:** 23102A0003  
**Domain:** DevOps Engineering & Continuous Delivery  
**Application Stack:** Java 17/22, Spring Boot 3.3.4, Thymeleaf, H2 Database, Maven, Selenium WebDriver 4, Jenkins CI/CD, Docker, Ansible

---

## 🎯 Project Overview
An automated, production-ready DevOps implementation for campus event management (Tech Fests, Hackathons, Workshops, Cultural Competitions). The platform replaces paper forms and spreadsheets with instant registration pass generation, atomic duplicate checking, and coordinator moderation workflows, backed by a complete CI/CD pipeline, automated browser testing, containerization, and configuration management.

---

## 🚀 Quick Start Guide

### 1. Run Application Locally
```powershell
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```
Open your browser at:
- **Web Portal:** [http://localhost:8080/](http://localhost:8080/)
- **Registration Form:** [http://localhost:8080/register](http://localhost:8080/register)
- **Status Tracker:** [http://localhost:8080/status](http://localhost:8080/status) (Test Roll No: `23102A0003`)
- **Admin Dashboard:** [http://localhost:8080/admin](http://localhost:8080/admin)
- **H2 Database Console:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console) (JDBC URL: `jdbc:h2:mem:eventregdb`)
- **Actuator Health Probe:** [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

### 2. Run Test Suite (Unit + Integration + Headless Selenium)
```powershell
# Run all tests (13 tests including Selenium journeys)
.\mvnw.cmd test
```

### 3. Package Production Executable JAR
```powershell
.\mvnw.cmd package -DskipTests
# Generated JAR: target/college-event-registration-platform.jar
```

### 4. Build & Run via Docker
```powershell
# Build multi-stage Docker image
docker build -t event-reg-platform:1.0.0 .

# Run containerized application
docker run -d --name college-event-platform -p 8080:8080 event-reg-platform:1.0.0

# Verify container health
docker inspect --format='{{json .State.Health}}' college-event-platform
```

### 5. Multi-Container Orchestration (App + Nginx Reverse Proxy)
```powershell
docker-compose up -d
```

### 6. Run Automated Git Branching & Conflict Simulation
```powershell
# Demonstrates Milestones 4, 5, 6
.\git-demo-workflow.ps1
```

---

## 📋 15-Milestone Curriculum Compliance Matrix

| Milestone | Deliverable Description | Documentation Reference |
|---|---|---|
| **1. Problem Definition & Scope** | Problem statement, stakeholders, constraints, 15-task MVP scope | [docs/01_PROBLEM_DEFINITION_AND_SCOPE.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/01_PROBLEM_DEFINITION_AND_SCOPE.md) |
| **2. Agile Planning & DevOps** | User stories, Gherkin acceptance criteria, Kanban plan, DoD, Mermaid DevOps lifecycle | [docs/02_AGILE_PLANNING_AND_DEVOPS_WORKFLOW.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/02_AGILE_PLANNING_AND_DEVOPS_WORKFLOW.md) |
| **3. Architecture & Tech Setup** | SRS, Use-case diagram, Component architecture, ER model, REST API catalog | [docs/03_SRS_AND_ARCHITECTURE.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/03_SRS_AND_ARCHITECTURE.md) |
| **4. Git & GitHub Repo Init** | Repo structure, `.gitignore`, `.github/` templates, branch policy, initial commits | [docs/04_GIT_WORKFLOW_AND_BRANCHING.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/04_GIT_WORKFLOW_AND_BRANCHING.md) |
| **5. Feature Branching Workflow** | `feature/registration-form`, commit history, simulated PR & review comments, merge | [docs/04_GIT_WORKFLOW_AND_BRANCHING.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/04_GIT_WORKFLOW_AND_BRANCHING.md) |
| **6. MVP Completion & Conflict** | MVP functions, simulated merge conflict creation and resolution, release tag `v1.0.0` | [docs/04_GIT_WORKFLOW_AND_BRANCHING.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/04_GIT_WORKFLOW_AND_BRANCHING.md) |
| **7. Jenkins Installation & CI** | Jenkins installation guide, Maven CI build job, commit polling/webhook, artifact archiving | [docs/05_JENKINS_CI_PIPELINE_SETUP.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/05_JENKINS_CI_PIPELINE_SETUP.md) |
| **8. Pipeline as Code** | Declarative `Jenkinsfile` with 8 stages, parameterized environment, deployment | [Jenkinsfile](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/Jenkinsfile) |
| **9. Selenium E2E Test Suite** | 4 critical user journeys, JUnit 5 assertions, failure screenshot listener | [docs/06_SELENIUM_TEST_SUITE.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/06_SELENIUM_TEST_SUITE.md) |
| **10. Continuous Testing Gate** | Jenkins Selenium quality gate, test reports, defect injection & resolution rerun | [docs/06_SELENIUM_TEST_SUITE.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/06_SELENIUM_TEST_SUITE.md) |
| **11. Docker Container Lifecycle** | Multi-stage Dockerfile, build, tag, port map, inspect, logs, restart, stop, clean scripts | [docker/docker-lifecycle.ps1](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docker/docker-lifecycle.ps1) |
| **12. Jenkins-Docker CD** | `Jenkinsfile.docker` continuous delivery pipeline with versioned tagging & auto-deploy | [Jenkinsfile.docker](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/Jenkinsfile.docker) |
| **13. Configuration Management** | Ansible inventory, `setup-server.yml` provisioning packages, users, folders, ports | [ansible/setup-server.yml](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/ansible/setup-server.yml) |
| **14. Provisioning & Rollback** | `deploy-app.yml` demonstrating idempotency, health probe, and `rollback-app.yml` | [ansible/deploy-app.yml](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/ansible/deploy-app.yml) |
| **15. Release, Report & Viva** | Master academic report (`PROJECT_REPORT.md`), troubleshooting, 30+ Viva Voce Q&As | [PROJECT_REPORT.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/PROJECT_REPORT.md) |

---

## 📂 Project Structure

```
college-event-registration-platform/
├── .github/
│   ├── ISSUE_TEMPLATE/
│   │   ├── bug_report.md
│   │   └── feature_request.md
│   └── PULL_REQUEST_TEMPLATE.md
├── ansible/
│   ├── ansible.cfg
│   ├── inventory.ini
│   ├── setup-server.yml
│   ├── deploy-app.yml
│   └── rollback-app.yml
├── docker/
│   ├── nginx.conf
│   ├── docker-lifecycle.ps1
│   └── docker-lifecycle.sh
├── docs/
│   ├── 01_PROBLEM_DEFINITION_AND_SCOPE.md
│   ├── 02_AGILE_PLANNING_AND_DEVOPS_WORKFLOW.md
│   ├── 03_SRS_AND_ARCHITECTURE.md
│   ├── 04_GIT_WORKFLOW_AND_BRANCHING.md
│   ├── 05_JENKINS_CI_PIPELINE_SETUP.md
│   ├── 06_SELENIUM_TEST_SUITE.md
│   ├── 07_DOCKER_CONTAINERIZATION.md
│   ├── 08_ANSIBLE_CONFIGURATION_MANAGEMENT.md
│   ├── 09_TROUBLESHOOTING_GUIDE.md
│   └── 10_VIVA_VOCE_PREPARATION_GUIDE.md
├── src/
│   ├── main/
│   │   ├── java/com/college/eventreg/
│   │   │   ├── CollegeEventRegistrationApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── WebController.java
│   │   │   │   ├── RegistrationApiController.java
│   │   │   │   └── AdminApiController.java
│   │   │   ├── model/
│   │   │   │   ├── Event.java
│   │   │   │   ├── Registration.java
│   │   │   │   └── RegistrationStatus.java
│   │   │   ├── repository/
│   │   │   │   ├── EventRepository.java
│   │   │   │   └── RegistrationRepository.java
│   │   │   ├── service/
│   │   │   │   └── EventRegistrationService.java
│   │   │   └── dto/
│   │   │       ├── RegistrationRequestDto.java
│   │   │       ├── StatusCheckDto.java
│   │   │       └── ApprovalRequestDto.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       │   ├── css/styles.css
│   │       │   └── js/scripts.js
│   │       └── templates/
│   │           ├── index.html
│   │           ├── register.html
│   │           ├── confirmation.html
│   │           ├── status.html
│   │           └── admin.html
│   └── test/
│       └── java/com/college/eventreg/
│           ├── CollegeEventRegistrationApplicationTests.java
│           ├── controller/RegistrationControllerTest.java
│           ├── service/EventRegistrationServiceTest.java
│           └── selenium/
│               ├── BaseSeleniumTest.java
│               ├── RegistrationJourneyTest.java
│               ├── ValidationJourneyTest.java
│               ├── StatusTrackingJourneyTest.java
│               └── AdminApprovalJourneyTest.java
├── CONTRIBUTING.md
├── Dockerfile
├── docker-compose.yml
├── git-demo-workflow.ps1
├── git-demo-workflow.sh
├── Jenkinsfile
├── Jenkinsfile.docker
├── mvnw
├── mvnw.cmd
├── pom.xml
├── PROJECT_REPORT.md
└── README.md
```

---

## 🎓 Academic Viva Voce Highlights
Before presenting your project to the examiner, review [docs/10_VIVA_VOCE_PREPARATION_GUIDE.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/10_VIVA_VOCE_PREPARATION_GUIDE.md) for 30+ answers covering:
- **DevOps Lifecycle & Continuous Integration**
- **Quality Gates & Fail-Safe Pipeline Design**
- **Multi-Stage Docker Optimization & Least Privilege User Security**
- **Ansible Idempotency & Automated Rollback Triggers**
- **Selenium Headless E2E Automation & Screenshot Evidence**

<!-- Feature: Registration Form v1.0 Completed -->
Feature Branch B: Enhanced QR-code scanner and badge generator.
Feature Branch A: Enhanced real-time participant badge generator.
