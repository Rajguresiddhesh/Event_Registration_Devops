package com.college.eventreg.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationRequestDto {

    @NotNull(message = "Event selection is mandatory")
    private Long eventId;

    @NotBlank(message = "Student name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String studentName;

    @NotBlank(message = "Student roll number is mandatory")
    @Pattern(regexp = "^[A-Za-z0-9]{5,20}$", message = "Roll number must be alphanumeric (5-20 characters)")
    private String studentRollNumber;

    @NotBlank(message = "Email address is mandatory")
    @Email(message = "Please provide a valid email address")
    private String studentEmail;

    @NotBlank(message = "Department selection is mandatory")
    private String studentDepartment;

    @NotBlank(message = "Contact phone number is mandatory")
    @Pattern(regexp = "^[0-9+()\\-\\s]{7,20}$", message = "Please provide a valid phone number")
    private String studentPhone;

    public RegistrationRequestDto() {
    }

    public RegistrationRequestDto(Long eventId, String studentName, String studentRollNumber,
                                  String studentEmail, String studentDepartment, String studentPhone) {
        this.eventId = eventId;
        this.studentName = studentName;
        this.studentRollNumber = studentRollNumber;
        this.studentEmail = studentEmail;
        this.studentDepartment = studentDepartment;
        this.studentPhone = studentPhone;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRollNumber() {
        return studentRollNumber;
    }

    public void setStudentRollNumber(String studentRollNumber) {
        this.studentRollNumber = studentRollNumber;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentDepartment() {
        return studentDepartment;
    }

    public void setStudentDepartment(String studentDepartment) {
        this.studentDepartment = studentDepartment;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }
}
