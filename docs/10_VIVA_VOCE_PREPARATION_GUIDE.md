# Viva Voce Preparation Guide (30+ Questions & Answers)

**Student Details:**  
- **Candidate Name:** SIDDHESH RAJGURE  
- **Roll Number:** 23102A0003  
- **Project Title:** Jenkins-Based College Event Registration Platform

---

## Part 1: DevOps & Agile Planning (Q1 - Q5)

### Q1: What is DevOps and why did you choose it for this College Event Platform?
**Answer:** DevOps is a cultural and engineering methodology combining Software Development (Dev) and IT Operations (Ops) to shorten the systems development life cycle while delivering continuous features, fixes, and updates in high quality. For this project, DevOps eliminates manual registration bottlenecks by providing automated continuous integration (Jenkins), automated regression testing (Selenium), reproducible packaging (Docker), and idempotent server provisioning (Ansible).

### Q2: Explain the Definition of Done (DoD) in your project.
**Answer:** In our Agile sprint, a task is only considered "Done" when:
1. Feature code passes local unit and integration tests with 100% pass rate.
2. Selenium E2E user journeys execute cleanly in headless mode without UI regressions.
3. Code is reviewed via Pull Request and merged into the baseline.
4. The Jenkins CI pipeline successfully builds, runs tests, and archives artifacts.
5. A Docker image is created and passes actuator health check probes.

### Q3: What is the difference between Continuous Integration, Continuous Delivery, and Continuous Deployment?
**Answer:**
- **Continuous Integration (CI):** Developers frequently merge code into a shared repository, where automated builds and tests verify changes immediately.
- **Continuous Delivery (CD):** Code changes are automatically built, tested, and packaged into a deployable state (e.g. Docker image), ready for human approval to release to production.
- **Continuous Deployment:** Every change that passes all stages of the production pipeline is released automatically to production without human intervention.

### Q4: What are the primary stages in your DevOps lifecycle diagram?
**Answer:** Plan -> Code -> Build (Maven) -> Test (JUnit 5 & Selenium) -> Release (Git tag v1.0.0) -> Deploy (Docker / Jenkins CD) -> Operate (Ansible) -> Monitor (Spring Boot Actuator `/actuator/health`).

### Q5: How does your platform prevent duplicate event registrations?
**Answer:** At the database and service layer, `RegistrationRepository.existsByEventIdAndStudentRollNumberIgnoreCase()` performs an atomic check. If a student roll number (e.g., `23102A0003`) is already registered for that specific event ID, the service immediately throws an `IllegalArgumentException` and rejects the submission.

---

## Part 2: Version Control & Git Collaboration (Q6 - Q10)

### Q6: What branching model did you implement?
**Answer:** We implemented a Git Feature Branching model:
- `main`: Production-ready release baseline (tagged `v1.0.0`).
- `develop`: Integration branch where tested feature branches converge.
- `feature/*`: Dedicated branches for distinct features (e.g., `feature/registration-form`, `feature/status-tracking`).
- `bugfix/*`: Defect isolation branches.

### Q7: How did you simulate and resolve a Git merge conflict?
**Answer:** We branched two parallel feature branches (`conflict-branch-a` and `conflict-branch-b`) from `develop` and modified the same lines in `README.md`. When merging the second branch into develop, Git reported `CONFLICT (content)`. We inspected the conflict markers (`<<<<<<<`, `=======`, `>>>>>>>`), manually combined the desired features, staged the file using `git add`, and committed the resolved merge.

### Q8: What is the purpose of Git tagging (`git tag`)?
**Answer:** Git tags create permanent, immutable checkpoints marking specific release points in repository history (e.g. `v1.0.0`). In CI/CD pipelines, tags trigger automated release builds and Docker image tagging.

### Q9: Why are `.gitignore` and issue templates important in team projects?
**Answer:** `.gitignore` prevents unneeded binaries, build output (`target/`), and local secrets from polluting the repository. Issue templates standardize bug reports and feature requests so developers have reproducible steps and environment details.

### Q10: What is the difference between `git merge` and `git rebase`?
**Answer:** `git merge` preserves the complete historical timeline and records a merge commit joining two branches. `git rebase` rewrites commit history by moving feature commits onto the tip of the base branch, producing a linear commit history.

---

## Part 3: Jenkins CI/CD & Pipeline as Code (Q11 - Q16)

### Q11: What is a Declarative Pipeline vs. a Scripted Pipeline in Jenkins?
**Answer:** A Declarative Pipeline provides a structured, opinionated syntax enclosed within a `pipeline { ... }` block with predefined sections like `agent`, `stages`, `steps`, and `post`. A Scripted Pipeline is based on Groovy syntax executed within a `node { ... }` block, offering arbitrary programming control but higher complexity. Declarative is the modern industry standard.

### Q12: How is your `Jenkinsfile` structured?
**Answer:** Our `Jenkinsfile` contains 8 stages:
1. Checkout SCM & Environment Audit
2. Build & Compile Application
3. Unit & Integration Testing
4. Selenium Automated Quality Gate
5. Package Application Artifact (`mvn package`)
6. Archive Build Artifacts (Fingerprinted JAR and test screenshots)
7. Automated Server Deployment
8. Post-Deploy Health Check Probe

### Q13: How does the quality gate work in your Jenkins pipeline?
**Answer:** If any JUnit or Selenium test fails, Maven Surefire exits with a non-zero exit code. Jenkins detects this exit code and halts the build immediately before packaging or deployment stages execute. The `post { failure { ... } }` block triggers failure logging.

### Q14: How did you parameterize your Jenkins build?
**Answer:** In the `parameters { ... }` block of `Jenkinsfile`, we defined:
- `ENVIRONMENT`: Choice parameter (`staging`, `production`, `testing`)
- `APP_PORT`: String parameter (default `8080`)
- `RUN_SELENIUM`: Boolean parameter to control automated browser testing

### Q15: How can Jenkins trigger builds automatically?
**Answer:** Jenkins can be triggered by:
1. GitHub Webhooks (`push` events trigger instant builds).
2. SCM Polling (`H/5 * * * *` checks for new commits every 5 minutes).
3. Scheduled Cron triggers (`H 2 * * *` for nightly builds).

### Q16: How did you archive build artifacts in Jenkins?
**Answer:** Using the `archiveArtifacts` step:
`archiveArtifacts artifacts: 'target/*.jar', fingerprint: true` and `archiveArtifacts artifacts: 'target/selenium-screenshots/*.png', allowEmptyArchive: true`.

---

## Part 4: Selenium Test Automation (Q17 - Q21)

### Q17: Why did you choose Selenium WebDriver for this project?
**Answer:** Selenium WebDriver interacts directly with the browser's native engine to simulate real user interactions (typing, button clicks, dropdown selection, URL navigation) across actual UI journeys, ensuring our Thymeleaf templates and client-side validations function seamlessly.

### Q18: What is headless browser execution and why is it used in CI?
**Answer:** Headless mode runs Chrome or Edge without rendering a physical graphical user interface (GUI) on screen (`--headless=new`). This is essential in CI environments like Jenkins or Docker runners where no display monitor or X11 server is present.

### Q19: Name the 4 critical user journeys tested by your Selenium suite.
**Answer:**
1. **Journey 1:** Complete student registration leading to digital pass generation (`RegistrationJourneyTest`).
2. **Journey 2:** Form validation rejection on blank/malformed fields (`ValidationJourneyTest`).
3. **Journey 3:** Real-time participant status search by roll number (`StatusTrackingJourneyTest`).
4. **Journey 4:** Organizer admin panel inspection and moderation actions (`AdminApprovalJourneyTest`).

### Q20: How does your failure screenshot mechanism operate?
**Answer:** In `BaseSeleniumTest.java`, if an assertion fails or an unexpected exception is thrown, the test calls `TakesScreenshot.getScreenshotAs(OutputType.BYTES)` and writes the PNG file to `target/selenium-screenshots/failure-<testName>-<timestamp>.png`.

### Q21: How did you handle WebDriver synchronization in tests?
**Answer:** We used implicit and explicit waits (`Duration.ofSeconds(5)`) to wait for DOM elements to load and be interactable before executing assertions, avoiding `NoSuchElementException` or race conditions.

---

## Part 5: Docker & Containerization (Q22 - Q26)

### Q22: Explain the multi-stage Dockerfile used in this project.
**Answer:**
- **Stage 1 (Builder):** Uses `maven:3.9.6-eclipse-temurin-17` to compile code and build the JAR.
- **Stage 2 (Runtime):** Uses a lightweight `eclipse-temurin:17-jre-jammy` image and copies only the compiled JAR from Stage 1.
- **Benefits:** Keeps the final production container small (~200MB vs ~800MB), removes build tools and source code from production, and drastically reduces vulnerability exposure.

### Q23: Why do you create an unprivileged user (`appuser`) in the Dockerfile?
**Answer:** Running containers as `root` is a critical security vulnerability. If an attacker breaches the application, they could gain root privileges on the host kernel. Creating `appuser` (UID 1001) restricts container execution to unprivileged mode.

### Q24: What is the purpose of the `HEALTHCHECK` directive in Docker?
**Answer:** `HEALTHCHECK` periodically tests whether the container is healthy by invoking `curl -f http://localhost:8080/actuator/health`. If the probe fails, Docker marks the container state as `unhealthy`, allowing orchestrators (Docker Compose/Kubernetes) to restart it automatically.

### Q25: Explain the complete Docker container lifecycle commands.
**Answer:**
- `docker build`: Compiles image layers.
- `docker run`: Creates and launches an active container process.
- `docker ps`: Lists running containers and port mappings.
- `docker logs`: Streams container stdout/stderr logs.
- `docker restart`: Restarts container process.
- `docker stop`: Sends graceful `SIGTERM`.
- `docker rm`: Deletes container filesystem.

### Q26: How does `docker-compose.yml` coordinate the application with Nginx?
**Answer:** `docker-compose.yml` defines both `event-app` and `nginx-proxy` within an isolated bridge network (`event-net`). Nginx listens on port 80 and reverse-proxies HTTP requests to `http://event-app:8080`, providing caching, header forwarding, and SSL termination capabilities.

---

## Part 6: Ansible Configuration Management (Q27 - Q31)

### Q27: What is Ansible and why is it called "agentless"?
**Answer:** Ansible is an open-source IT automation engine used for configuration management, provisioning, and application deployment. It is agentless because it connects to target servers using standard SSH (or local execution) and requires no background daemon or agent installed on target nodes.

### Q28: What is Idempotency in Ansible and why is it critical?
**Answer:** An Ansible operation is idempotent if executing it multiple times produces the exact same end result without duplicate side-effects. For example, creating a directory that already exists will report `ok` and `changed=0` instead of failing or creating duplicates.

### Q29: What tasks does `ansible/setup-server.yml` perform?
**Answer:**
1. Updates package cache.
2. Installs dependencies (`openjdk-17-jre-headless`, `docker.io`, `curl`, `ufw`).
3. Creates dedicated group `appgroup` and user `appuser`.
4. Creates directory hierarchy (`/opt/college-event-platform/{bin,config,logs,backups}`).
5. Configures UFW firewall rules for ports 8080 and 80.
6. Starts and enables Docker service.

### Q30: How does your rollback playbook (`rollback-app.yml`) handle failed releases?
**Answer:** If a health-check probe fails post-deployment, the rollback playbook stops the degraded container, pulls and launches the previously tagged stable image (`1.0.0-stable`), waits for application warmup, verifies the health endpoint returns HTTP 200, and updates the release audit file.

### Q31: What are Ansible Playbooks, Plays, Tasks, and Modules?
**Answer:**
- **Playbook:** YAML file containing one or more plays.
- **Play:** Maps a set of tasks to a targeted group of hosts from the inventory.
- **Task:** An individual action to execute on the host.
- **Module:** The underlying Python program executed by Ansible to perform the task (e.g. `apt`, `file`, `user`, `docker_container`, `uri`).
