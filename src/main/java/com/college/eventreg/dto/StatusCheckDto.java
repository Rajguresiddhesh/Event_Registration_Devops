package com.college.eventreg.dto;

public class StatusCheckDto {

    private String query; // Can be Registration Code or Roll Number

    public StatusCheckDto() {
    }

    public StatusCheckDto(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
