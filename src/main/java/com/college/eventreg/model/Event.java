package com.college.eventreg.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String eventDate;

    @Column(nullable = false, length = 100)
    private String venue;

    @Column(nullable = false)
    private Integer maxCapacity;

    @Column(nullable = false)
    private Integer currentRegistrations = 0;

    @Column(nullable = false)
    private Double fee = 0.0;

    public Event() {
    }

    public Event(String title, String category, String description, String eventDate, String venue, Integer maxCapacity, Double fee) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.venue = venue;
        this.maxCapacity = maxCapacity;
        this.fee = fee;
        this.currentRegistrations = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Integer getCurrentRegistrations() {
        return currentRegistrations;
    }

    public void setCurrentRegistrations(Integer currentRegistrations) {
        this.currentRegistrations = currentRegistrations;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public boolean isFull() {
        return currentRegistrations != null && maxCapacity != null && currentRegistrations >= maxCapacity;
    }

    public void incrementRegistrations() {
        if (this.currentRegistrations == null) {
            this.currentRegistrations = 1;
        } else {
            this.currentRegistrations++;
        }
    }

    public void decrementRegistrations() {
        if (this.currentRegistrations != null && this.currentRegistrations > 0) {
            this.currentRegistrations--;
        }
    }
}
