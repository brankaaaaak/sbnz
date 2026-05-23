package com.ftn.sbnz.model.facts;

import com.ftn.sbnz.model.enums.BloodPressure;

public class BloodPressureFact {
    private Long callId;
    private BloodPressure category;
    public BloodPressureFact(Long callId, BloodPressure category) { 
        this.callId = callId; 
        this.category = category; 
    }
    public Long getCallId() { return callId; }
    public void setCallId(Long callId) { this.callId = callId; }
    public BloodPressure getCategory() { return category; }
    public void setCategory(BloodPressure category) { this.category = category; }
}
