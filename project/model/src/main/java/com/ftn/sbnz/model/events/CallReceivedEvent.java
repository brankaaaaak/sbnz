package com.ftn.sbnz.model.events;

public class CallReceivedEvent {
    private Long callId;
    private long timestamp;

    public CallReceivedEvent() {}

    public CallReceivedEvent(Long callId, long timestamp) {
        this.callId = callId;
        this.timestamp = timestamp;
    }

    public Long getCallId() {
        return callId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
