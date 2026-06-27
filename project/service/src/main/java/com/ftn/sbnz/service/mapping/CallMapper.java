package com.ftn.sbnz.service.mapping;


import org.springframework.stereotype.Component;

import com.ftn.sbnz.model.assessment.PatientAssessment;
import com.ftn.sbnz.model.models.Call;
import com.ftn.sbnz.service.dto.CallDTO;


@Component
public class CallMapper {
    
    public CallDTO toDTO(Call call, PatientAssessment patientAssessment){
        CallDTO dto = new CallDTO();

        dto.setId(call.getId());
        dto.setLocation(call.getLocation());
        dto.setCallTime(call.getCallTime());
        dto.setIncidentType(call.getIncidentType());

        if (call.getPatient() != null) {
            dto.setName(call.getPatient().getName());
        }

        dto.setFinalLevel(patientAssessment.getFinalLevel());

        return dto;
    }
}
