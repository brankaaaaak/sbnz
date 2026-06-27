package com.ftn.sbnz.model.symptoms;

@SymptomType("STING")
public class StingSymptoms {
    private Long callId;

    private boolean choking;
    private boolean systemicSwelling;
    private boolean skinReaction;

    private boolean previousSevereReaction;

    public StingSymptoms(Long callId,
                         boolean choking,
                         boolean systemicSwelling,
                         boolean skinReaction,
                         boolean previousSevereReaction) {
        this.callId = callId;
        this.choking = choking;
        this.systemicSwelling = systemicSwelling;
        this.skinReaction = skinReaction;
        this.previousSevereReaction = previousSevereReaction;
    }

    public Long getCallId() { return callId; }
    public boolean isChoking() { return choking; }
    public boolean isSystemicSwelling() { return systemicSwelling; }
    public boolean isSkinReaction() { return skinReaction; }
    public boolean isPreviousSevereReaction() { return previousSevereReaction; }
}
