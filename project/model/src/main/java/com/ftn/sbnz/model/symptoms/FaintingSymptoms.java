package com.ftn.sbnz.model.symptoms;

@SymptomType("FAINTING")
public class FaintingSymptoms {
    private Long callId;
    private boolean diabetes;
    private boolean tookTherapy;
    private boolean painEarlier;

    public FaintingSymptoms() {}

    public FaintingSymptoms(Long callId, boolean diabetes, boolean tookTherapy, boolean painEarlier){
        this.callId = callId;
        this.diabetes = diabetes;
        this.tookTherapy = tookTherapy;
        this.painEarlier = painEarlier;
    }

    public Long getCallId() {
        return callId;
    }

    public void setCallId(Long callId) {
        this.callId = callId;
    }

    public boolean isDiabetes(){
        return diabetes;
    }

    public void setDiabetes(boolean diabetes){
        this.diabetes = diabetes;
    }

    public boolean isTookTherapy(){
        return tookTherapy;
    }

    public void setTookTherapy(boolean tookTherapy){
        this.tookTherapy = tookTherapy;
    }

    public boolean isPainEarlier(){
        return painEarlier;
    }

    public void setPainEarlier(boolean painEarlier){
        this.painEarlier = painEarlier;
    }

}
