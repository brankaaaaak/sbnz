package com.ftn.sbnz.model.assessment;

import com.ftn.sbnz.model.enums.BloodPressure;
import com.ftn.sbnz.model.enums.ConsciousnessLevel;
import com.ftn.sbnz.model.enums.EmergencyLevel;

public class PatientAssessment {
    private Long callId;
    private boolean elevatedPulse;        // zaključak nivoa 1
    private boolean elevatedTemperature;  // zaključak nivoa 1
    private boolean irregularBreathing;   // zaključak nivoa 1
    private BloodPressure bloodPressure;          // zaključak nivoa 1
    private ConsciousnessLevel consciousnessLevel;  //zaključak nivoa 1
    
    private EmergencyLevel preliminaryLevel; // zaključak nivoa 2

    private EmergencyLevel finalLevel; //nivo 3
    private boolean finalized = false;
    
    public PatientAssessment() {
    }

    public Long getCallId() {
        return callId; 
    }
    
    public void setCallId(Long callId) { 
        this.callId = callId; 
    }
    
    public boolean isElevatedPulse() {
        return elevatedPulse;
    }

    public void setElevatedPulse(boolean elevatedPulse) {
        this.elevatedPulse = elevatedPulse;
    }

    public boolean isElevatedTemperature() {
        return elevatedTemperature;
    }

    public void setElevatedTemperature(boolean elevatedTemperature) {
        this.elevatedTemperature = elevatedTemperature;
    }

    public boolean isIrregularBreathing() {
        return irregularBreathing;
    }

    public void setIrregularBreathing(boolean irregularBreathing) {
        this.irregularBreathing = irregularBreathing;
    }

    public BloodPressure getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(BloodPressure bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public ConsciousnessLevel getConsciousnessLevel() {
        return consciousnessLevel;
    }

    public void setConsciousnessLevel(ConsciousnessLevel consciousnessLevel) {
        this.consciousnessLevel = consciousnessLevel;
    }

    public EmergencyLevel getPreliminaryLevel() {
        return preliminaryLevel;
    }

    public void setPreliminaryLevel(EmergencyLevel preliminaryLevel) {
        this.preliminaryLevel = preliminaryLevel;
    }

    public EmergencyLevel getFinalLevel() {
        return finalLevel;
    }

    public void setFinalLevel(EmergencyLevel finalLevel) {
        this.finalLevel = finalLevel;
    }    
     
    public boolean isFinalized(){
        return finalized;
    }

    public void setFinalized(boolean finalized){
        this.finalized = finalized;
    }

}
