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
import com.ftn.sbnz.model.symptoms.InjuryHeadSymptoms;
import com.ftn.sbnz.model.symptoms.StingSymptoms;

@Service
public class EmergencyService {

    @Autowired
    private KieContainer kieContainer;

    public Call testStingYellowWithPreviousReactionToRed() {
        KieSession kieSession = kieContainer.newKieSession();

        // 1. Pacijent sa nepravilnim disanjem (breathingRegular = false)
        //    Ovo će aktivirati Nivo2 pravilo "STING + nepravilno disanje -> YELLOW"
        VitalSigns vs = new VitalSigns(
            180,    
            120,    
            80,     
            36.6,   
            false,   // breathingRegular = false (nepravilno disanje)
            ConsciousnessLevel.CONSCIOUS);

        Patient patient = new Patient(
            1L,
            "Test Patient",
            30,
            vs
        );

        // 2. Poziv tipa STING
        Call call = new Call(
            4L,
            "Test location",
            LocalDateTime.now(),
            IncidentType.STING,
            Status.IN_PROGRESS,
            patient
        );

        // 3. StingSymptoms sa prethodnom teškom reakcijom (previousSevereReaction = true)
        StingSymptoms symptoms = new StingSymptoms(
            call.getId(),
            false,   // choking
            false,   // systemic swelling
            false,   // skin reaction
            true     // previousSevereReaction - ključno za override u RED
        );

        kieSession.insert(call);
        kieSession.insert(symptoms);
        kieSession.fireAllRules();
        kieSession.dispose();

        return call;
    }
    // 1. Test za pravilo: Nivo3 - Head injury RED/YELLOW + open wound -> RED
    public Call testHeadInjuryRedYellowOpenWoundToRed() {
        KieSession kieSession = kieContainer.newKieSession();

        // Pacijent: UNCONSCIOUS_RESPONSIVE -> Nivo2 će postaviti YELLOW
        VitalSigns vs = new VitalSigns(120, 80, 70, 36.6, true, ConsciousnessLevel.UNCONSCIOUS_RESPONSIVE);
        Patient patient = new Patient(1L, "Head patient", 30, vs);
        Call call = new Call(10L, "Test location", LocalDateTime.now(),
                IncidentType.INJURY_HEAD, Status.IN_PROGRESS, patient);

        // Simptomi: otvorena rana = true, povraćanje = false
        InjuryHeadSymptoms symptoms = new InjuryHeadSymptoms(call.getId(), false, true);

        kieSession.insert(call);
        kieSession.insert(symptoms);
        kieSession.fireAllRules();
        kieSession.dispose();

        //System.out.println("Final emergency level: " + call.getEmergencyLevel()); // Treba RED
        return call;
    }

    // 2. Test za pravilo: Nivo3 - Head injury GREEN + open wound -> YELLOW
    public Call testHeadInjuryGreenOpenWoundToYellow() {
        KieSession kieSession = kieContainer.newKieSession();

        // Pacijent: CONSCIOUS -> Nivo2 će postaviti GREEN
        VitalSigns vs = new VitalSigns(120, 80, 70, 36.6, true, ConsciousnessLevel.CONSCIOUS);
        Patient patient = new Patient(2L, "Head patient", 25, vs);
        Call call = new Call(11L, "Test location", LocalDateTime.now(),
                IncidentType.INJURY_HEAD, Status.IN_PROGRESS, patient);

        // Simptomi: otvorena rana = true, povraćanje = false
        InjuryHeadSymptoms symptoms = new InjuryHeadSymptoms(call.getId(), false, true);

        kieSession.insert(call);
        kieSession.insert(symptoms);
        kieSession.fireAllRules();
        kieSession.dispose();

        //System.out.println("Final emergency level: " + call.getEmergencyLevel()); // Treba YELLOW
        return call;
    }

    // 3. Test za pravilo: Nivo3 - Head injury GREEN + vomiting -> RED
    public Call testHeadInjuryGreenVomitingToRed() {
        KieSession kieSession = kieContainer.newKieSession();

        // Pacijent: CONSCIOUS -> Nivo2 će postaviti GREEN
        VitalSigns vs = new VitalSigns(120, 80, 70, 36.6, true, ConsciousnessLevel.CONSCIOUS);
        Patient patient = new Patient(3L, "Head patient", 40, vs);
        Call call = new Call(12L, "Test location", LocalDateTime.now(), 
                IncidentType.INJURY_HEAD, Status.IN_PROGRESS, patient);

        // Simptomi: povraćanje = true (otvorena rana može biti bilo koja)
        InjuryHeadSymptoms symptoms = new InjuryHeadSymptoms(call.getId(), true, true);

        kieSession.insert(call);
        kieSession.insert(symptoms);
        kieSession.fireAllRules();
        kieSession.dispose();

        //System.out.println("Final emergency level: " + call.getEmergencyLevel()); // Treba RED
        return call;
    }
}