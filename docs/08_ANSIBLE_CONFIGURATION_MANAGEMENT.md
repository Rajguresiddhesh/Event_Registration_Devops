# Milestones 13 & 14: Ansible Configuration Management & Reliability Validation

**Student:** 23102A0003 — SIDDHESH RAJGURE  
**Project Title:** Jenkins-Based College Event Registration Platform

---

## 1. Server Prerequisites Specification (Milestone 13)
The Ansible automation manages all requirements for hosting the application:
- **Operating Packages:** `openjdk-17-jre-headless`, `curl`, `git`, `docker.io`, `ufw`, `tar`.
- **System Accounts:** User `appuser` (UID 1001), Group `appgroup` (GID 1001) with docker group membership.
- **Directory Hierarchy:**
  - `/opt/college-event-platform` (base deployment directory)
  - `/opt/college-event-platform/bin` (executable scripts)
  - `/opt/college-event-platform/config` (application environment configs)
  - `/opt/college-event-platform/logs` (application logs)
  - `/opt/college-event-platform/backups` (versioned JAR/container backups)
- **Firewall & Ports:** TCP `8080` (Spring Boot), TCP `80` (HTTP reverse proxy).
- **Service daemons:** `docker` service enabled and started.

---

## 2. Ansible Playbook Execution Commands

### A. Server Setup & Hardening (Milestone 13)
```bash
ansible-playbook -i ansible/inventory.ini ansible/setup-server.yml --syntax-check
ansible-playbook -i ansible/inventory.ini ansible/setup-server.yml
```

### B. Application Deployment & Idempotency Demonstration (Milestone 14)
```bash
# First Run: Deploys container and provisions directories (Reported: changed=5, ok=6)
ansible-playbook -i ansible/inventory.ini ansible/deploy-app.yml

# Second Run: Rerunning automation to demonstrate Idempotency (Reported: changed=0, ok=6)
ansible-playbook -i ansible/inventory.ini ansible/deploy-app.yml
```
**Idempotency Evidence:** Rerunning the playbook produces **`changed=0`** because directories already exist, users are configured, and the container state matches the declared specification.

---

## 3. Automated Rollback & Disaster Recovery Demonstration (Milestone 14)
If a faulty build fails post-deployment health checks, the automated rollback playbook is invoked:
```bash
ansible-playbook -i ansible/inventory.ini ansible/rollback-app.yml
```
**Execution Output:**
```
PLAY [Emergency Rollback to Previous Stable Release] **************************************

TASK [1. Log Rollback Trigger Incident] ****************************************************
ok: [target-node-staging] => {
    "msg": "CRITICAL: Triggering automated rollback to previous stable version 1.0.0-stable due to health probe failure!"
}

TASK [2. Stop and remove degraded container] ***********************************************
changed: [target-node-staging]

TASK [3. Restore and run previous stable container release] *********************************
changed: [target-node-staging]

TASK [4. Pause for application warmup] *****************************************************
Pausing for 6 seconds

TASK [5. Verify restored system health] ****************************************************
ok: [target-node-staging] => { "status": 200 }

TASK [6. Update release audit registry] ****************************************************
changed: [target-node-staging]

PLAY RECAP ********************************************************************************
target-node-staging        : ok=6    changed=3    unreachable=0    failed=0    skipped=0
```
