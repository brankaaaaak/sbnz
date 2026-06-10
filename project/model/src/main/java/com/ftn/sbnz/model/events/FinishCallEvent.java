package com.ftn.sbnz.model.events;

public class FinishCallEvent {
    
    private Long callId;

    public FinishCallEvent() {}

    public FinishCallEvent(Long callId) {
        this.callId = callId;
    }

    public Long getCallId() {
        return callId;
    }

    public void setCallId(Long callId) {
        this.callId = callId;
    }
    
}
