package com.ftn.sbnz.model.models;

import java.time.LocalDateTime;

public class Call {
    private Long id;
    private String location;
    private LocalDateTime callTime;
    private int numberOfPatients;
    private IncidentType incidentType;
    private Status status;          
    private EmergencyLevel emergencyLevel;
    private Patient patient;
    
    public Call() {
    }

    public Call(Long id,
                String location,
                LocalDateTime callTime,
                int numberOfPatients,
                IncidentType incidentType,
                Status status,
                EmergencyLevel emergencyLevel,
                Patient patient) {

        this.id = id;
        this.location = location;
        this.callTime = callTime;
        this.numberOfPatients = numberOfPatients;
        this.incidentType = incidentType;
        this.status = status;
        this.emergencyLevel = emergencyLevel;
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

    public int getNumberOfPatients() {
        return numberOfPatients;
    }

    public void setNumberOfPatients(int numberOfPatients) {
        this.numberOfPatients = numberOfPatients;
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

    public EmergencyLevel getEmergencyLevel() {
        return emergencyLevel;
    }

    public void setEmergencyLevel(EmergencyLevel emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
