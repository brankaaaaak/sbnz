package com.ftn.sbnz.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.enums.ConsciousnessLevel;
import com.ftn.sbnz.model.enums.IncidentType;
import com.ftn.sbnz.model.models.Call;
import com.ftn.sbnz.service.services.EmergencyService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class EmergencyController {

    @Autowired
    private EmergencyService emergencyService;
    
    @GetMapping("/sting-test")
    public Call sting() {
        return emergencyService.testStingYellowWithPreviousReactionToRed();
    }

    @GetMapping("/head-test1")
    public Call head1() {
        return emergencyService.testHeadInjuryGreenOpenWoundToYellow();
    }

    @GetMapping("/head-test2")
    public Call head2() {
        return emergencyService.testHeadInjuryGreenVomitingToRed();
    }

    @GetMapping("/head-test3")
    public Call head3() {
        return emergencyService.testHeadInjuryRedYellowOpenWoundToRed();
    }

    @GetMapping("/enums/incident-types")
    public List<IncidentType> getIncidentTypes() {
        return List.of(IncidentType.values());
    }

    @GetMapping("/enums/consciousness-levels")
    public List<ConsciousnessLevel> getLevels() {
        return List.of(ConsciousnessLevel.values());
    }
    @PostMapping("/calls")
    public Call createCall(@RequestBody Call call) {
        //return callService.save(call);
        return null;
    }
}
