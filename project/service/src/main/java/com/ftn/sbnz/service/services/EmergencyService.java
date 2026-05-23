package com.ftn.sbnz.service.services;

import java.time.LocalDateTime;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.enums.ConsciousnessLevel;
import com.ftn.sbnz.model.enums.IncidentType;
import com.ftn.sbnz.model.enums.Status;
import com.ftn.sbnz.model.models.Call;
import com.ftn.sbnz.model.models.Patient;
import com.ftn.sbnz.model.models.VitalSigns;

@Service
public class EmergencyService {

    @Autowired
    private KieContainer kieContainer;

    public void testRules() {
        KieSession kieSession = kieContainer.newKieSession();

        VitalSigns vitalSigns = new VitalSigns(
            125,    // pulse - povisen
            115,    // systolic
            75,     // diastolic
            36.8,   // temperatura - normalna
            false   // disanje nepravilno
        );

        Patient patient = new Patient(
            1L,
            "Test Pacijent",
            35,
            ConsciousnessLevel.UNCONSCIOUS_RESPONSIVE,
            false,
            false,
            false,
            vitalSigns
        );

        Call call = new Call(
            1L,
            "Ulica test",
            LocalDateTime.now(),
            1,
            IncidentType.FAINTING,
            Status.PENDING,
            null,
            patient
        );

        kieSession.insert(call);
        kieSession.fireAllRules();
        kieSession.dispose();
    }
}