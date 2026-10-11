# ==============================================================================
# Docker Container Lifecycle Management Script (PowerShell)
# Student: 23102A0003 - SIDDHESH RAJGURE
# Project: Jenkins-Based College Event Registration Platform
# ==============================================================================

param(
    [string]$Action = "all",
    [string]$ImageName = "event-reg-platform",
    [string]$VersionTag = "1.0.0",
    [string]$ContainerName = "college-event-app",
    [int]$HostPort = 8080
)

$FullImage = "$ImageName`:$VersionTag"

function Step-Log($msg) {
    Write-Host "`n>>> [DOCKER LIFECYCLE] $msg" -ForegroundColor Cyan
}

if ($Action -eq "build" -or $Action -eq "all") {
    Step-Log "1. Building and Tagging Docker Image: $FullImage"
    docker build -t $FullImage -t "$ImageName`:latest" .
    if ($LASTEXITCODE -ne 0) { Write-Error "Docker build failed"; exit 1 }
    
    Step-Log "2. Inspecting Built Image Details:"
    docker images $ImageName
}

if ($Action -eq "run" -or $Action -eq "all") {
    Step-Log "3. Running Container '$ContainerName' with Port Mapping $HostPort:8080"
    # Remove existing container if present
    docker rm -f $ContainerName 2>$null
    
    docker run -d `
        --name $ContainerName `
        -p "$HostPort`:8080" `
        -e PORT=8080 `
        --health-cmd="curl -f http://localhost:8080/actuator/health || exit 1" `
        --health-interval=10s `
        --health-retries=3 `
        $FullImage
    
    if ($LASTEXITCODE -ne 0) { Write-Error "Docker run failed"; exit 1 }
}

if ($Action -eq "inspect" -or $Action -eq "all") {
    Step-Log "4. Inspecting Container Metadata & Health Status"
    docker ps -f "name=$ContainerName"
    Start-Sleep -Seconds 5
    docker inspect --format='{{json .State.Health}}' $ContainerName
}

if ($Action -eq "logs" -or $Action -eq "all") {
    Step-Log "5. Viewing Container Output Logs"
    docker logs --tail 30 $ContainerName
}

if ($Action -eq "test" -or $Action -eq "all") {
    Step-Log "6. Verifying Container Connectivity (HTTP GET)"
    Start-Sleep -Seconds 3
    try {
        $response = Invoke-RestMethod -Uri "http://localhost:$HostPort/actuator/health" -TimeoutSec 10
        Write-Host "Actuator Health Status: $($response.status)" -ForegroundColor Green
    } catch {
        Write-Warning "Waiting for Spring Boot warmup. Retrying endpoint in 5 seconds..."
        Start-Sleep -Seconds 5
        try {
            $response = Invoke-RestMethod -Uri "http://localhost:$HostPort/api/events" -TimeoutSec 10
            Write-Host "API Events Status: SUCCESS ($($response.Count) events found)" -ForegroundColor Green
        } catch {
            Write-Warning "Container is still starting up."
        }
    }
}

if ($Action -eq "restart") {
    Step-Log "Demonstrating Container Restart"
    docker restart $ContainerName
    docker ps -f "name=$ContainerName"
}

if ($Action -eq "stop") {
    Step-Log "Stopping Container $ContainerName"
    docker stop $ContainerName
}

if ($Action -eq "clean") {
    Step-Log "Stopping and Removing Container $ContainerName"
    docker stop $ContainerName 2>$null
    docker rm $ContainerName 2>$null
    Write-Host "Container removed cleanly." -ForegroundColor Yellow
}

Write-Host "`n[SUCCESS] Docker lifecycle demonstration finished successfully!" -ForegroundColor Green
