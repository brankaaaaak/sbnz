package com.ftn.sbnz.model.models;

import com.ftn.sbnz.model.enums.ConsciousnessLevel;

public class Patient {
    private Long id;
    private int age;
    private String name;
    private ConsciousnessLevel consciousnessLevel;
    private VitalSigns vitalSigns;
    
    public Patient() {
    }

    public Patient(Long id,
                   String name,
                   int age,
                   ConsciousnessLevel consciousnessLevel,
                   VitalSigns vitalSigns) {

        this.id = id;
        this.name = name;
        this.age = age;
        this.consciousnessLevel = consciousnessLevel;
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

    public ConsciousnessLevel getConsciousnessLevel() {
        return consciousnessLevel; 
    }

    public void setConsciousnessLevel(ConsciousnessLevel consciousnessLevel) { 
        this.consciousnessLevel = consciousnessLevel; 
    }

    public VitalSigns getVitalSigns() {
        return vitalSigns;
    }

    public void setVitalSigns(VitalSigns vitalSigns) {
        this.vitalSigns = vitalSigns;
    }
}
