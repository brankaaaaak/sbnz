package com.ftn.sbnz.model.facts;

public class ElevatedTemperature {
    private Long callId;
    public ElevatedTemperature(Long callId) { this.callId = callId; }
    public Long getCallId() { return callId; }
    public void setCallId(Long callId) { this.callId = callId; }
}
