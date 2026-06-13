package com.ftn.sbnz.model.events;

public class SystemAlert {
    
    private String type;

    public SystemAlert() {}

    public SystemAlert(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
