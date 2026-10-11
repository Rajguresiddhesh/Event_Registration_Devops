# Multi-Stage Dockerfile for Jenkins-Based College Event Registration Platform
# Student: 23102A0003 - SIDDHESH RAJGURE

# ==========================================
# Stage 1: Build & Package Stage
# ==========================================
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Cache dependency layer
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy application source code and compile
COPY src ./src
RUN mvn clean package -DskipTests -B

# ==========================================
# Stage 2: Production Lightweight Runtime
# ==========================================
FROM eclipse-temurin:17-jre-jammy
LABEL maintainer="SIDDHESH RAJGURE <23102A0003>"
LABEL description="College Event Registration Platform Container"
LABEL version="1.0.0"

# Create unprivileged application user for security hardening
RUN groupadd -g 1001 appgroup && \
    useradd -u 1001 -g appgroup -s /bin/false -m appuser

WORKDIR /app

# Copy packaged JAR artifact from builder
COPY --from=builder /workspace/target/college-event-registration-platform.jar app.jar
RUN chown -R appuser:appgroup /app

# Switch to non-root user
USER appuser

# Expose HTTP port
EXPOSE 8080

# Configure Container Health Check via Spring Boot Actuator
HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Environment flags and JVM tuning
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"
ENV PORT=8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
