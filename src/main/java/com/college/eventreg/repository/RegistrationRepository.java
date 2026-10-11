package com.college.eventreg.repository;

import com.college.eventreg.model.Registration;
import com.college.eventreg.model.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    
    Optional<Registration> findByRegistrationCode(String registrationCode);
    
    List<Registration> findByStudentRollNumberIgnoreCase(String studentRollNumber);
    
    List<Registration> findByStudentEmailIgnoreCase(String studentEmail);
    
    List<Registration> findByEventId(Long eventId);
    
    List<Registration> findByStatus(RegistrationStatus status);
    
    boolean existsByEventIdAndStudentRollNumberIgnoreCase(Long eventId, String studentRollNumber);
    
    long countByStatus(RegistrationStatus status);
}
