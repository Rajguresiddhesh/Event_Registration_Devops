# Contributing & Branching Guidelines

**Student:** 23102A0003 — SIDDHESH RAJGURE  
**Project:** Jenkins-Based College Event Registration Platform

---

## 1. Branch Naming Policy
All developers must adhere to standardized git branch naming conventions:
- **`main`**: Production-ready, deployed release baseline. Only fast-forward merges via reviewed PRs.
- **`develop`**: Integration branch where completed feature branches converge.
- **`feature/<feature-name>`**: Core features (e.g., `feature/registration-form`, `feature/status-tracking`, `feature/admin-approval`).
- **`bugfix/<issue-id>-<short-description>`**: Defect corrections (e.g., `bugfix/issue-12-phone-regex`).
- **`release/v<x.y.z>`**: Release stabilization branches (e.g., `release/v1.0.0`).

---

## 2. Commit Message Convention
Follow Conventional Commits specification:
- `feat: add student registration form with client validation`
- `fix: correct regex pattern for student phone validation`
- `test: add Selenium WebDriver end-to-end journey for status tracking`
- `ci: configure declarative Jenkinsfile with multi-stage quality gates`
- `docker: create multi-stage Dockerfile with non-root appuser`
- `ansible: add automated provisioning and rollback playbooks`

---

## 3. Pull Request & Review Policy
1. Every PR requires at least one approving peer review.
2. The automated Jenkins CI pipeline must report **SUCCESS** (all unit and Selenium tests passing).
3. Merge conflicts must be rebased and resolved locally prior to PR merge.
