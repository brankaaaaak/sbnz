package com.ftn.sbnz.service.dto;

import java.util.List;
import com.ftn.sbnz.model.enums.EmergencyLevel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalAssessmentResponse {
    private EmergencyLevel finalLevel;
    private List<String> reasons;
    private Long callId;
}
