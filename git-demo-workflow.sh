#!/usr/bin/env bash
# ==============================================================================
# Git Collaboration & Branching Demonstration Script (Bash)
# Demonstrates Milestones 4, 5, and 6
# Student: 23102A0003 - SIDDHESH RAJGURE
# ==============================================================================
set -e

echo ">>> Initializing Git Repository..."
git init
git config user.name "Siddhesh Rajgure"
git config user.email "23102A0003@college.edu"

git checkout -B main
git add .
git commit -m "chore: initial project skeleton with Spring Boot, Maven, and CI artifacts"

git checkout -b develop
git checkout -b feature/registration-form
echo -e "\n<!-- Feature: Registration Form v1.0 Completed -->" >> README.md
git add README.md
git commit -m "feat(ui): implement dynamic registration form with validation and styling"

git checkout develop
git merge --no-ff feature/registration-form -m "merge: pull request #1 from feature/registration-form into develop"

# Tag release
git checkout main
git merge --no-ff develop -m "release: merge develop into main for production baseline v1.0.0"
git tag -a v1.0.0 -m "Release v1.0.0 - Production MVP for SIDDHESH RAJGURE (23102A0003)"

echo -e "\n>>> Git Log Visual Tree Evidence:"
git log --graph --oneline --decorate -n 10
echo -e "\n[SUCCESS] Git repository initialized and tagged v1.0.0!"
