package com.ftn.sbnz.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.enums.ConsciousnessLevel;
import com.ftn.sbnz.model.enums.IncidentType;
import com.ftn.sbnz.model.models.Call;
import com.ftn.sbnz.service.dto.FinalAssessmentResponse;
import com.ftn.sbnz.service.dto.PreliminaryAssessmentResponse;
import com.ftn.sbnz.service.dto.SymptomFieldDTO;
import com.ftn.sbnz.service.dto.SymptomsRequest;
import com.ftn.sbnz.service.services.EmergencyService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class EmergencyController {

    @Autowired
    private EmergencyService emergencyService;

    @GetMapping("/enums/incident-types")
    public List<IncidentType> getIncidentTypes() {
        return List.of(IncidentType.values());
    }

    @GetMapping("/enums/consciousness-levels")
    public List<ConsciousnessLevel> getLevels() {
        return List.of(ConsciousnessLevel.values());
    }
    @PostMapping("/assessment/preliminary")
    public PreliminaryAssessmentResponse preliminary(
            @RequestBody Call call) {

        return emergencyService.calculatePreliminary(call);
    }
    
    @GetMapping("/symptoms/schema/{incidentType}")
    public List<SymptomFieldDTO> getSchema(@PathVariable IncidentType incidentType) {
        return emergencyService.getSchema(incidentType);
    }

    @PostMapping("/assessment/symptoms")
    public ResponseEntity<FinalAssessmentResponse> processSymptoms(@RequestBody SymptomsRequest request) {
        return ResponseEntity.ok(emergencyService.calculateFinal(request));
    }
}
