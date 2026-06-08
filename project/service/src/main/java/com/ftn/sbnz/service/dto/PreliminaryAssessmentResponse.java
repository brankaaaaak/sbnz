package com.ftn.sbnz.service.dto;

import java.util.List;

import com.ftn.sbnz.model.enums.EmergencyLevel;
import com.ftn.sbnz.model.enums.IncidentType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreliminaryAssessmentResponse {
    private Long callId;

    private EmergencyLevel preliminaryLevel;

    private List<String> reasons;

    private IncidentType incidentType;
}
