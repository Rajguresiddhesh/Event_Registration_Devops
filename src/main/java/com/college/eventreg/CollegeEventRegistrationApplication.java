package com.college.eventreg;

import com.college.eventreg.model.Event;
import com.college.eventreg.model.Registration;
import com.college.eventreg.model.RegistrationStatus;
import com.college.eventreg.repository.EventRepository;
import com.college.eventreg.repository.RegistrationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class CollegeEventRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollegeEventRegistrationApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(EventRepository eventRepository, RegistrationRepository registrationRepository) {
        return args -> {
            if (eventRepository.count() == 0) {
                Event hackathon = new Event(
                        "National Tech Symposium & Hackathon 2026",
                        "Technical",
                        "24-Hour flagship inter-college hackathon focusing on AI, Cloud Native, and DevOps innovations.",
                        "2026-11-15",
                        "Main Auditorium & Computer Labs",
                        120,
                        0.0
                );

                Event devopsSummit = new Event(
                        "AI & Cloud DevOps Summit",
                        "Workshop",
                        "Hands-on masterclass on CI/CD pipelines, Docker containerization, Jenkins automation, and Infrastructure as Code.",
                        "2026-11-20",
                        "Seminar Hall B",
                        80,
                        0.0
                );

                Event roboWars = new Event(
                        "RoboWars: National Autonomous Robotics Arena",
                        "Technical",
                        "High-octane robotics combat and obstacle evasion challenge judged by industry roboticists.",
                        "2026-11-22",
                        "Indoor Sports Complex",
                        50,
                        150.0
                );

                Event resonance = new Event(
                        "Resonance 2026: Annual Cultural Extravaganza",
                        "Cultural",
                        "Music, drama, literary debates, and photography competitions celebrating college creative talent.",
                        "2026-11-28",
                        "Open Air Amphitheatre",
                        250,
                        0.0
                );

                eventRepository.save(hackathon);
                eventRepository.save(devopsSummit);
                eventRepository.save(roboWars);
                eventRepository.save(resonance);

                // Initial Seed Registration for Student 23102A0003 Siddhesh Rajgure
                Registration demoReg = new Registration(
                        "REG-2026-SR001",
                        hackathon,
                        "SIDDHESH RAJGURE",
                        "23102A0003",
                        "siddhesh.rajgure@college.edu",
                        "Computer Science & Engineering",
                        "+91 9876543210"
                );
                demoReg.setStatus(RegistrationStatus.APPROVED);
                demoReg.setAdminRemarks("Verified college credentials. Registration confirmed.");
                registrationRepository.save(demoReg);

                hackathon.incrementRegistrations();
                eventRepository.save(hackathon);

                System.out.println("=================================================================");
                System.out.println("College Event Registration Platform Initialized Successfully!");
                System.out.println("Student: SIDDHESH RAJGURE (Roll: 23102A0003)");
                System.out.println("Loaded " + eventRepository.count() + " Events and Seed Registrations.");
                System.out.println("Access Web Portal: http://localhost:8080/");
                System.out.println("=================================================================");
            }
        };
    }
}
