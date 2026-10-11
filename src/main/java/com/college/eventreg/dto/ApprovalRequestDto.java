package com.college.eventreg.dto;

import com.college.eventreg.model.RegistrationStatus;

public class ApprovalRequestDto {

    private RegistrationStatus status;
    private String adminRemarks;

    public ApprovalRequestDto() {
    }

    public ApprovalRequestDto(RegistrationStatus status, String adminRemarks) {
        this.status = status;
        this.adminRemarks = adminRemarks;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    public String getAdminRemarks() {
        return adminRemarks;
    }

    public void setAdminRemarks(String adminRemarks) {
        this.adminRemarks = adminRemarks;
    }
}
