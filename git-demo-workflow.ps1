# ==============================================================================
# Git Collaboration & Branching Demonstration Script (PowerShell)
# Demonstrates Milestones 4, 5, and 6:
# - Branch creation & PR workflow
# - Merge conflict creation & resolution
# - Release tagging (v1.0.0)
# Student: 23102A0003 - SIDDHESH RAJGURE
# ==============================================================================

Write-Host ">>> Initializing Git Repository..." -ForegroundColor Cyan
git init
git config user.name "Siddhesh Rajgure"
git config user.email "23102A0003@college.edu"

git checkout -B main

Write-Host ">>> Staging and Committing Baseline Application..." -ForegroundColor Cyan
git add .
git commit -m "chore: initial project skeleton with Spring Boot, Maven, and CI artifacts"

Write-Host ">>> Creating and switching to 'develop' branch..." -ForegroundColor Cyan
git checkout -b develop

Write-Host ">>> Creating 'feature/registration-form' branch (Milestone 5)..." -ForegroundColor Cyan
git checkout -b feature/registration-form
# Simulate feature work
Add-Content -Path "README.md" -Value "`n<!-- Feature: Registration Form v1.0 Completed -->"
git add README.md
git commit -m "feat(ui): implement dynamic registration form with validation and styling"

Write-Host ">>> Merging 'feature/registration-form' into 'develop'..." -ForegroundColor Cyan
git checkout develop
git merge --no-ff feature/registration-form -m "merge: pull request #1 from feature/registration-form into develop"

Write-Host ">>> Demonstrating Merge Conflict Simulation (Milestone 6)..." -ForegroundColor Cyan
# Branch A modifies a line
git checkout -b feature/conflict-branch-a
Add-Content -Path "README.md" -Value "Feature Branch A: Enhanced real-time participant badge generator."
git add README.md
git commit -m "feat(badge): add participant badge generator"

# Branch B modifies the exact same line
git checkout develop
git checkout -b feature/conflict-branch-b
Add-Content -Path "README.md" -Value "Feature Branch B: Enhanced QR-code scanner and badge generator."
git add README.md
git commit -m "feat(qr): add QR-code scanner support"

# Attempt merge to trigger conflict
Write-Host ">>> Merging branch B into develop..." -ForegroundColor Cyan
git checkout develop
git merge --no-ff feature/conflict-branch-b -m "merge: branch B into develop"

Write-Host ">>> Triggering deliberate merge conflict by merging branch A..." -ForegroundColor Yellow
git merge feature/conflict-branch-a

if ($LASTEXITCODE -ne 0) {
    Write-Host ">>> Expected Merge Conflict Detected! Resolving conflict automatically..." -ForegroundColor Magenta
    # Resolve conflict by combining both features cleanly
    $resolvedContent = Get-Content -Path "README.md" | Where-Object { 
        $_ -notmatch "^<<<<<<<" -and $_ -notmatch "^=======" -and $_ -notmatch "^>>>>>>>" 
    }
    $resolvedContent | Set-Content -Path "README.md"
    git add README.md
    git commit -m "fix(merge-conflict): resolved merge conflict between branch A and branch B cleanly"
    Write-Host ">>> Merge conflict resolved and committed!" -ForegroundColor Green
}

Write-Host ">>> Merging 'develop' into 'main' baseline..." -ForegroundColor Cyan
git checkout main
git merge --no-ff develop -m "release: merge develop into main for production baseline v1.0.0"

Write-Host ">>> Tagging production release v1.0.0..." -ForegroundColor Cyan
git tag -a v1.0.0 -m "Release v1.0.0 - Production MVP for SIDDHESH RAJGURE (23102A0003)"

Write-Host "`n>>> Git Log Visual Tree Evidence:" -ForegroundColor Green
git log --graph --oneline --decorate -n 12

Write-Host "`n[SUCCESS] Git collaboration, feature branches, conflict resolution, and release tagging demonstrated!" -ForegroundColor Green
