package com.ftn.sbnz.model.models;

import com.ftn.sbnz.model.enums.ConsciousnessLevel;

public class VitalSigns {

    private int pulse;
    private int systolicPressure;
    private int diastolicPressure;
    private double temperature;
    private boolean breathingRegular;
    private ConsciousnessLevel consciousnessLevel;

    public VitalSigns() {
    }

    public VitalSigns(int pulse,
                      int systolicPressure,
                      int diastolicPressure,
                      double temperature,
                      boolean breathingRegular,
                      ConsciousnessLevel consciousnessLevel) {

        this.pulse = pulse;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.temperature = temperature;
        this.breathingRegular = breathingRegular;
        this.consciousnessLevel = consciousnessLevel;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean isBreathingRegular() {
        return breathingRegular;
    }

    public void setBreathingRegular(boolean breathingRegular) {
        this.breathingRegular = breathingRegular;
    }

    public ConsciousnessLevel getConsciousnessLevel() {
        return consciousnessLevel;
    }

    public void setConsciousnessLevel(ConsciousnessLevel consciousnessLevel) {
        this.consciousnessLevel = consciousnessLevel;
    }

}
