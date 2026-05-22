package com.ftn.sbnz.model.decision;

import java.util.ArrayList;
import java.util.List;

import com.ftn.sbnz.model.enums.EmergencyLevel;

public class FinalDecision {
    
    private Long callId;
    private EmergencyLevel emergencyLevel;
    private List<String> advice;          
    private String explanation;

    public FinalDecision() {
        this.advice = new ArrayList<>();
    }

    public FinalDecision(Long callId) {
        this.callId = callId;
        this.advice = new ArrayList<>();
    }

    public void addAdvice(String a) {
        this.advice.add(a);
    }

    public Long getCallId() { return callId; }
    public void setCallId(Long callId) { this.callId = callId; }

    public EmergencyLevel getEmergencyLevel() { return emergencyLevel; }
    public void setEmergencyLevel(EmergencyLevel emergencyLevel) { this.emergencyLevel = emergencyLevel; }

    public List<String> getAdvice() { return advice; }
    public void setAdvice(List<String> advice) { this.advice = advice; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
}
