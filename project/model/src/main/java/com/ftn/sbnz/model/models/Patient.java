package com.ftn.sbnz.model.models;

public class Patient {
    private Long id;
    private int age;
    private String name;
    private boolean conscious;
    private boolean hasDiabetes;
    private boolean hasHemophilia;
    private boolean hasArrhythmia;
    private VitalSigns vitalSigns;
    
    public Patient() {
    }

    public Patient(Long id,
                   String name,
                   int age,
                   boolean conscious,
                   boolean hasDiabetes,
                   boolean hasHemophilia,
                   boolean hasArrhythmia,
                   VitalSigns vitalSigns) {

        this.id = id;
        this.name = name;
        this.age = age;
        this.conscious = conscious;
        this.hasDiabetes = hasDiabetes;
        this.hasHemophilia = hasHemophilia;
        this.hasArrhythmia = hasArrhythmia;
        this.vitalSigns = vitalSigns;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConscious() {
        return conscious;
    }

    public void setConscious(boolean conscious) {
        this.conscious = conscious;
    }

    public boolean isHasDiabetes() {
        return hasDiabetes;
    }

    public void setHasDiabetes(boolean hasDiabetes) {
        this.hasDiabetes = hasDiabetes;
    }

    public boolean isHasHemophilia() {
        return hasHemophilia;
    }

    public void setHasHemophilia(boolean hasHemophilia) {
        this.hasHemophilia = hasHemophilia;
    }

    public boolean isHasArrhythmia() {
        return hasArrhythmia;
    }

    public void setHasArrhythmia(boolean hasArrhythmia) {
        this.hasArrhythmia = hasArrhythmia;
    }

    public VitalSigns getVitalSigns() {
        return vitalSigns;
    }

    public void setVitalSigns(VitalSigns vitalSigns) {
        this.vitalSigns = vitalSigns;
    }
}
