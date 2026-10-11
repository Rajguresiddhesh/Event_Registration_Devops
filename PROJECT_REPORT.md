# Master Project Report: Jenkins-Based College Event Registration Platform

**Academic Session:** 2026  
**Candidate Name:** SIDDHESH RAJGURE  
**Student Roll Number:** 23102A0003  
**Domain:** DevOps Engineering, Continuous Integration & Infrastructure Automation  
**Repository Baseline:** `https://github.com/Rajguresiddhesh/Event_Registration_Devops`

---

## Executive Summary

The **Jenkins-Based College Event Registration Platform** is a full-stack, enterprise-grade DevOps implementation engineered to modernize campus event onboarding, attendee verification, and seat allocation. Traditional campus event registration suffers from manual errors, duplicate entries, lack of instant status verification, and unreliable server deployments.

This project delivers a complete DevOps engineering solution spanning all **15 curriculum milestones**:
1. Full-featured **Java 17/22 Spring Boot 3** web application with embedded Tomcat and in-memory/persisted H2 database.
2. Robust input validation, duplicate entry prevention, unique registration pass code generation, real-time status tracking, and an organizer moderation console.
3. Feature-branch Git workflow with documented Pull Request reviews, merge conflict simulation and resolution, and production release tagging (`v1.0.0`).
4. Declarative **`Jenkinsfile`** continuous integration and automated delivery pipelines with multi-stage quality gates and parameterization.
5. Automated **Selenium WebDriver 4** headless test suite covering 4 end-to-end user journeys with automated screenshot capture on failure.
6. Multi-stage, security-hardened **Docker** containerization with non-root execution and Spring Boot Actuator health checks.
7. Agentless **Ansible** configuration management playbooks demonstrating idempotent server provisioning, live health monitoring, and automated disaster rollback.

---

## Table of Contents
1. [Milestone 1: Problem Definition & Scope](#milestone-1-problem-definition--scope)
2. [Milestone 2: Agile Planning & DevOps Workflow](#milestone-2-agile-planning--devops-workflow)
3. [Milestone 3: Requirements & System Architecture](#milestone-3-requirements--system-architecture)
4. [Milestone 4: Git & GitHub Repository Initialization](#milestone-4-git--github-repository-initialization)
5. [Milestone 5: Feature Branching & PR Collaboration](#milestone-5-feature-branching--pr-collaboration)
6. [Milestone 6: MVP Completion, Conflict Resolution & Tagging](#milestone-6-mvp-completion-conflict-resolution--tagging)
7. [Milestone 7: Jenkins Installation & CI Job](#milestone-7-jenkins-installation--ci-job)
8. [Milestone 8: Pipeline as Code & Deployment](#milestone-8-pipeline-as-code--deployment)
9. [Milestone 9: Selenium Test Suite Design & Execution](#milestone-9-selenium-test-suite-design--execution)
10. [Milestone 10: Continuous Testing Quality Gate in Jenkins](#milestone-10-continuous-testing-quality-gate-in-jenkins)
11. [Milestone 11: Docker Container Lifecycle Management](#milestone-11-docker-container-lifecycle-management)
12. [Milestone 12: Jenkins-Docker Continuous Deployment](#milestone-12-jenkins-docker-continuous-deployment)
13. [Milestone 13: Ansible Configuration Management](#milestone-13-ansible-configuration-management)
14. [Milestone 14: Automated Provisioning, Idempotency & Rollback](#milestone-14-automated-provisioning-idempotency--rollback)
15. [Milestone 15: Release, Viva Voce & Conclusion](#milestone-15-release-viva-voce--conclusion)

---

## Milestone 1: Problem Definition & Scope
- **Problem Statement:** Educational institutions lack unified digital workflows for student symposium registration. Google Forms lack atomic capacity constraints and status tracking, leading to overbooking and administrative bottlenecks.
- **Stakeholders:** Students, Event Faculty Coordinators, College Administration, DevOps Engineers.
- **Objectives:** Sub-200ms page load latency, 100% duplicate registration prevention, automated CI/CD pipeline, and single-command reproducible container deployments.
- **Constraints:** Java Spring Boot, Maven build automation, embedded Tomcat, H2 in-memory DB, headless browser automation, Docker containerization.
- **15-Task Scope:** Detailed in [docs/01_PROBLEM_DEFINITION_AND_SCOPE.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/01_PROBLEM_DEFINITION_AND_SCOPE.md).

---

## Milestone 2: Agile Planning & DevOps Workflow
- **User Stories & Acceptance Criteria:** Defined in Gherkin syntax covering Registration, Validation, Status Tracking, and Admin Moderation.
- **Sprint Board:** 15-task Kanban roadmap segmented into Backlog, In Progress, In Testing, and Done.
- **Definition of Done (DoD):** Passing unit tests (100%), passing Selenium E2E tests, clean Jenkins CI run, Docker container health probe passing.
- **DevOps Workflow Diagram:** Comprehensive Mermaid visualization tracking changes from Git commit through Jenkins quality gate to Ansible provisioning. Detailed in [docs/02_AGILE_PLANNING_AND_DEVOPS_WORKFLOW.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/02_AGILE_PLANNING_AND_DEVOPS_WORKFLOW.md).

---

## Milestone 3: Requirements & System Architecture
- **SRS Summary:** Detailed Functional (FR-1 to FR-6) and Non-Functional Requirements (NFR-1 to NFR-3).
- **Component Architecture:** MVC architecture with clean separation of Concerns:
  - Controllers (`WebController`, `RegistrationApiController`, `AdminApiController`)
  - Service Layer (`EventRegistrationService`)
  - Persistence Layer (`EventRepository`, `RegistrationRepository`, H2 DB)
- **Data Model:** `Event` (1) to `Registration` (N) relational mapping with unique registration codes.
- **API Catalog:** Full REST endpoint documentation. Detailed in [docs/03_SRS_AND_ARCHITECTURE.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/03_SRS_AND_ARCHITECTURE.md).

---

## Milestone 4: Git & GitHub Repository Initialization
- Standardized directory layout initialized with `.gitignore`, `CONTRIBUTING.md`, and issue templates (`.github/ISSUE_TEMPLATE/`).
- Enforced branch naming rules (`main`, `develop`, `feature/*`, `bugfix/*`, `release/*`).
- Baseline repository initialized with clear conventional commits.

---

## Milestone 5: Feature Branching & PR Collaboration
- First core workflow (`feature/registration-form`) branched from `develop`.
- Implemented registration form with client/server validation.
- Pull request simulated with code review comments and merged cleanly into `develop`.

---

## Milestone 6: MVP Completion, Conflict Resolution & Tagging
- Implemented remaining MVP components: Status Tracker and Organizer Moderation Console.
- Simulated deliberate merge conflict between parallel branches modifying `README.md`.
- Conflict resolved and committed.
- Merged to `main` and created annotated release tag: `v1.0.0`. Detailed in [docs/04_GIT_WORKFLOW_AND_BRANCHING.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/04_GIT_WORKFLOW_AND_BRANCHING.md).

---

## Milestone 7: Jenkins Installation & CI Job
- Configured Jenkins Freestyle Job with SCM Polling (`H/5 * * * *`).
- Built via Maven targets `clean package`.
- Preserved and archived build artifacts (`target/*.jar`) and JUnit reports (`target/surefire-reports/*.xml`).

---

## Milestone 8: Pipeline as Code & Deployment
- Implemented Declarative `Jenkinsfile` with 8 stages: Checkout -> Compile -> JUnit 5 Tests -> Selenium Quality Gate -> Package -> Archive Artifacts -> Deploy -> Health Check.
- Parameterized settings: `ENVIRONMENT` (`staging`/`production`), `APP_PORT` (`8080`), and `RUN_SELENIUM` (`true`/`false`). Detailed in [docs/05_JENKINS_CI_PIPELINE_SETUP.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/05_JENKINS_CI_PIPELINE_SETUP.md).

---

## Milestone 9: Selenium Test Suite Design & Execution
- Created 4 end-to-end user journeys executed locally via Maven Surefire:
  1. `RegistrationJourneyTest`: End-to-end form completion, registration code verification.
  2. `ValidationJourneyTest`: Assertion of required field rejection.
  3. `StatusTrackingJourneyTest`: Roll number lookup and badge verification.
  4. `AdminApprovalJourneyTest`: Dashboard participant roster verification.
- Implemented automated screenshot capture on failure (`target/selenium-screenshots/`).
- Test execution verified: **13 of 13 tests passed, 0 failures, 0 errors**.

---

## Milestone 10: Continuous Testing Quality Gate in Jenkins
- Integrated Selenium suite into Jenkins CI pipeline.
- Enforced quality gate: test failure stops pipeline before packaging or deployment.
- Documented defect injection scenario, build failure evidence, bugfix commit, and successful rerun. Detailed in [docs/06_SELENIUM_TEST_SUITE.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/06_SELENIUM_TEST_SUITE.md).

---

## Milestone 11: Docker Container Lifecycle Management
- Multi-stage `Dockerfile`: Maven builder stage + lightweight `eclipse-temurin:17-jre-jammy` runtime.
- Security hardening: unprivileged `appuser` (UID: 1001).
- Healthcheck: periodic probe to `/actuator/health`.
- Documented complete container lifecycle: `build`, `run`, `inspect`, `logs`, `restart`, `stop`, `rm`.
- Automated script: [docker-lifecycle.ps1](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docker/docker-lifecycle.ps1). Detailed in [docs/07_DOCKER_CONTAINERIZATION.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/07_DOCKER_CONTAINERIZATION.md).

---

## Milestone 12: Jenkins-Docker Continuous Deployment
- Extended CD pipeline via [Jenkinsfile.docker](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/Jenkinsfile.docker).
- Automates building semantic Docker images (`${REGISTRY}:${IMAGE_TAG}-${BUILD_NUMBER}` and `latest`).
- Automated zero-downtime container replacement with health check verification.

---

## Milestone 13: Ansible Configuration Management
- Defined inventory in [ansible/inventory.ini](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/ansible/inventory.ini).
- Server prerequisites playbook in [ansible/setup-server.yml](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/ansible/setup-server.yml):
  - Installs OpenJDK, Docker, UFW firewall.
  - Creates unprivileged system user `appuser` and group `appgroup`.
  - Configures directory tree (`/opt/college-event-platform/{bin,config,logs,backups}`).
  - Opens firewall ports `8080` and `80`.

---

## Milestone 14: Automated Provisioning, Idempotency & Rollback
- Application deployment playbook in [ansible/deploy-app.yml](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/ansible/deploy-app.yml).
- **Idempotency Demonstrated:** Second execution yields `changed=0`, proving safe re-execution without side-effects.
- Disaster recovery playbook in [ansible/rollback-app.yml](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/ansible/rollback-app.yml): automatically restores previous stable container release (`1.0.0-stable`) when health checks fail. Detailed in [docs/08_ANSIBLE_CONFIGURATION_MANAGEMENT.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/08_ANSIBLE_CONFIGURATION_MANAGEMENT.md).

---

## Milestone 15: Release, Viva Voce & Conclusion
- Complete technical documentation suite (10 modules in `docs/`).
- Viva Voce guide with 30+ comprehensive Q&As in [docs/10_VIVA_VOCE_PREPARATION_GUIDE.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/10_VIVA_VOCE_PREPARATION_GUIDE.md).
- Troubleshooting manual in [docs/09_TROUBLESHOOTING_GUIDE.md](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/docs/09_TROUBLESHOOTING_GUIDE.md).

### Limitations & Future Scope:
- **Limitations:** Currently uses H2 database in-memory/file mode; production would benefit from an external managed PostgreSQL/MySQL cluster.
- **Future Scope:** Implement OAuth2 / Single Sign-On (SSO) with college student email authentication; add SMS / WhatsApp registration pass notifications via Twilio API; deploy to Kubernetes with Helm charts.
