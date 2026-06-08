package com.ftn.sbnz.model.symptoms;

@SymptomType("INJURY_EXTREMITY")
public class InjuryExtremitySymptoms {

    private Long callId;
    private boolean openFracture;   // otvoren prelom
    private boolean bleeding;       // krvarenje

    public InjuryExtremitySymptoms() {}

    public InjuryExtremitySymptoms(Long callId, boolean openFracture, boolean bleeding) {
        this.callId = callId;
        this.openFracture = openFracture;
        this.bleeding = bleeding;
    }

    public Long getCallId() {
        return callId;
    }

    public void setCallId(Long callId) {
        this.callId = callId;
    }

    public boolean isOpenFracture() {
        return openFracture;
    }

    public void setOpenFracture(boolean openFracture) {
        this.openFracture = openFracture;
    }

    public boolean isBleeding() {
        return bleeding;
    }

    public void setBleeding(boolean bleeding) {
        this.bleeding = bleeding;
    }
}
