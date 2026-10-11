# Milestones 11 & 12: Docker Containerization and Jenkins-Docker CD

**Student:** 23102A0003 — SIDDHESH RAJGURE  
**Project Title:** Jenkins-Based College Event Registration Platform

---

## 1. Multi-Stage Dockerfile Architecture

The application utilizes a production-grade multi-stage build:
1. **Builder Stage (`maven:3.9.6-eclipse-temurin-17`):**
   - Resolves dependencies (`dependency:go-offline`).
   - Compiles and packages executable JAR (`mvn clean package -DskipTests`).
2. **Runtime Stage (`eclipse-temurin:17-jre-jammy`):**
   - Minimal attack surface.
   - Non-root user: `appuser` (UID: 1001) for security hardening.
   - Built-in `HEALTHCHECK` directive probing `/actuator/health`.
   - Tuned JVM container memory flags (`-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0`).

---

## 2. Docker Container Lifecycle Commands (Milestone 11)

| Lifecycle Stage | Docker Command | Purpose & Result |
|---|---|---|
| **Build & Tag** | `docker build -t event-reg-platform:1.0.0 -t event-reg-platform:latest .` | Compiles image into cached local registry layers. |
| **Inspect Image** | `docker images event-reg-platform` | Lists image ID, size, and creation timestamp. |
| **Run Container** | `docker run -d --name college-event-app -p 8080:8080 -e PORT=8080 event-reg-platform:1.0.0` | Starts detached container with host-to-container port mapping. |
| **Inspect State** | `docker ps -f "name=college-event-app"` | Verifies uptime and port binding `0.0.0.0:8080->8080/tcp`. |
| **Inspect Logs** | `docker logs --tail 30 college-event-app` | Shows Spring Boot banner and Tomcat startup log. |
| **Health Probe** | `docker inspect --format='{{json .State.Health}}' college-event-app` | Confirms status `"healthy"`. |
| **Restart** | `docker restart college-event-app` | Demonstrates graceful restart. |
| **Stop** | `docker stop college-event-app` | Sends `SIGTERM` and shuts down gracefully. |
| **Remove** | `docker rm college-event-app` | Cleans up container resource. |

*Automated script available:* `./docker/docker-lifecycle.ps1` or `./docker/docker-lifecycle.sh`

---

## 3. Multi-Container Orchestration (`docker-compose.yml`)
To support reverse-proxy routing and production load:
```yaml
services:
  event-app:
    build: .
    image: event-reg-platform:1.0.0
    container_name: college-event-platform
    ports:
      - "8080:8080"
  nginx-proxy:
    image: nginx:alpine
    ports:
      - "80:80"
    depends_on:
      - event-app
```
Launch both services:
```bash
docker-compose up -d
```

---

## 4. Jenkins-Docker Continuous Deployment Pipeline (Milestone 12)

[Jenkinsfile.docker](file:///C:/Users/Siddhesh/.gemini/antigravity/scratch/college-event-registration-platform/Jenkinsfile.docker) implements an end-to-end continuous deployment workflow:
1. **SCM Checkout:** Clones latest commit.
2. **Quality Gate:** Enforces test suite (`mvn test`).
3. **Versioned Image Build:** Builds image tagged with semantic version and build number: `siddheshrajgure/college-event-platform:1.0.0-${BUILD_NUMBER}` and `latest`.
4. **Registry Publication:** Pushes image to Docker Hub / private registry cache.
5. **Zero-Downtime Deployment:** Stops previous container, boots new image, maps port 8080.
6. **Automated Healthcheck:** Probes `http://localhost:8080/actuator/health` to confirm successful rollout.
