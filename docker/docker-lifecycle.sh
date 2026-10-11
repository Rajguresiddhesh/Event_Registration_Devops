#!/usr/bin/env bash
# ==============================================================================
# Docker Container Lifecycle Management Script (Bash / POSIX)
# Student: 23102A0003 - SIDDHESH RAJGURE
# Project: Jenkins-Based College Event Registration Platform
# ==============================================================================
set -e

ACTION="${1:-all}"
IMAGE_NAME="event-reg-platform"
VERSION_TAG="1.0.0"
CONTAINER_NAME="college-event-app"
HOST_PORT="8080"
FULL_IMAGE="${IMAGE_NAME}:${VERSION_TAG}"

log_step() {
  echo -e "\n\033[1;36m>>> [DOCKER LIFECYCLE] $1\033[0m"
}

if [ "$ACTION" = "build" ] || [ "$ACTION" = "all" ]; then
  log_step "1. Building and Tagging Docker Image: ${FULL_IMAGE}"
  docker build -t "${FULL_IMAGE}" -t "${IMAGE_NAME}:latest" .
  log_step "2. Inspecting Built Image Details:"
  docker images "${IMAGE_NAME}"
fi

if [ "$ACTION" = "run" ] || [ "$ACTION" = "all" ]; then
  log_step "3. Running Container '${CONTAINER_NAME}' on Port ${HOST_PORT}:8080"
  docker rm -f "${CONTAINER_NAME}" 2>/dev/null || true
  docker run -d \
    --name "${CONTAINER_NAME}" \
    -p "${HOST_PORT}:8080" \
    -e PORT=8080 \
    "${FULL_IMAGE}"
fi

if [ "$ACTION" = "inspect" ] || [ "$ACTION" = "all" ]; then
  log_step "4. Inspecting Container Metadata & State"
  docker ps -f "name=${CONTAINER_NAME}"
fi

if [ "$ACTION" = "logs" ] || [ "$ACTION" = "all" ]; then
  log_step "5. Displaying Container Output Logs"
  sleep 3
  docker logs --tail 25 "${CONTAINER_NAME}"
fi

if [ "$ACTION" = "test" ] || [ "$ACTION" = "all" ]; then
  log_step "6. Verifying Service HTTP Endpoint"
  sleep 4
  curl -s -f "http://localhost:${HOST_PORT}/actuator/health" || echo "Warmup in progress..."
fi

if [ "$ACTION" = "stop" ]; then
  log_step "Stopping Container"
  docker stop "${CONTAINER_NAME}"
fi

if [ "$ACTION" = "restart" ]; then
  log_step "Restarting Container"
  docker restart "${CONTAINER_NAME}"
fi

if [ "$ACTION" = "clean" ]; then
  log_step "Cleaning Up Container"
  docker stop "${CONTAINER_NAME}" 2>/dev/null || true
  docker rm "${CONTAINER_NAME}" 2>/dev/null || true
fi

echo -e "\n\033[1;32m[SUCCESS] Docker lifecycle script execution complete!\033[0m"
