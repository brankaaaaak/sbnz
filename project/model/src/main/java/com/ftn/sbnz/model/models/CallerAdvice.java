package com.ftn.sbnz.model.models;

import java.util.ArrayList;
import java.util.List;

public class CallerAdvice {
    
    private Long callId;
    private List<String> adviceList = new ArrayList<>();

    public CallerAdvice() {
    }

    public CallerAdvice(Long callId) {
        this.callId = callId;
    }

    public Long getCallId() {
        return callId;
    }

    public void setCallId(Long callId) {
        this.callId = callId;
    }

    public List<String> getAdviceList() {
        return adviceList;
    }

    public void addAdvice(String advice) {
        if (!adviceList.contains(advice)) {
            adviceList.add(advice);
        }
    }

    @Override
    public String toString() {
        return "CallerAdvice{callId=" + callId + ", adviceList=" + adviceList + "}";
    }
    
}
