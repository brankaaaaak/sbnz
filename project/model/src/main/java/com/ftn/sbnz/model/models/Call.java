package com.ftn.sbnz.model.models;

import java.time.LocalDateTime;

import com.ftn.sbnz.model.enums.IncidentType;
import com.ftn.sbnz.model.enums.Status;

public class Call {
    private Long id;
    private String location;
    private LocalDateTime callTime;
    private IncidentType incidentType;
    private Status status;          
    private Patient patient;
    
    public Call() {
    }

    public Call(Long id,
                String location,
                LocalDateTime callTime,
                IncidentType incidentType,
                Status status,
                Patient patient) {

        this.id = id;
        this.location = location;
        this.callTime = callTime;
        this.incidentType = incidentType;
        this.status = status;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCallTime() {
        return callTime;
    }

    public void setCallTime(LocalDateTime callTime) {
        this.callTime = callTime;
    }

    public IncidentType getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(IncidentType incidentType) {
        this.incidentType = incidentType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
