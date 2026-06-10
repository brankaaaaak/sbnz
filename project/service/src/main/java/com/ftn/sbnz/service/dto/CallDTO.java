package com.ftn.sbnz.service.dto;

import java.time.LocalDateTime;

import com.ftn.sbnz.model.enums.EmergencyLevel;
import com.ftn.sbnz.model.enums.IncidentType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallDTO {
    private Long id;
    private String location;
    private LocalDateTime callTime;
    private IncidentType incidentType;
    private String name;
    private EmergencyLevel finalLevel;
    
}
