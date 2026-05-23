package com.ftn.sbnz.model.facts;

import com.ftn.sbnz.model.enums.ConsciousnessLevel;

public class ConsciousnessFact {
    private Long callId;
    private ConsciousnessLevel level;
    public ConsciousnessFact(Long callId, ConsciousnessLevel level) { 
        this.callId = callId; 
        this.level = level; 
    }
    public Long getCallId() { return callId; }
    public void setCallId(Long callId) { this.callId = callId; }
    public ConsciousnessLevel getLevel() { return level; }
    public void setLevel(ConsciousnessLevel level) { this.level = level; }
}
