package com.ftn.sbnz.model.symptoms;

public class InjuryHeadSymptoms {
    private Long callId;
    private boolean vomiting;           // povraća
    private boolean openWound;          // otvorena rana

    public InjuryHeadSymptoms() {}

    public InjuryHeadSymptoms(Long callId, boolean vomiting, boolean openWound) {
        this.callId = callId;
        this.vomiting = vomiting;
        this.openWound = openWound;
    }

    public Long getCallId() { return callId; }
    public void setCallId(Long callId) { this.callId = callId; }
    public boolean isVomiting() { return vomiting; }
    public void setVomiting(boolean vomiting) { this.vomiting = vomiting; }
    public boolean isOpenWound() { return openWound; }
    public void setOpenWound(boolean openWound) { this.openWound = openWound; }
}
