package com.ftn.sbnz.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.models.Call;
import com.ftn.sbnz.service.services.EmergencyService;

@RestController
@RequestMapping("/api/emergency")
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


}
