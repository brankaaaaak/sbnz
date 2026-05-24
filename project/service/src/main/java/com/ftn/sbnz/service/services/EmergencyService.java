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
            false   // breathingRegular = false (nepravilno disanje)
        );

        Patient patient = new Patient(
            1L,
            "Test Patient",
            30,
            ConsciousnessLevel.CONSCIOUS,  // svjestan – potrebno za YELLOW
            false,
            false,
            false,
            vs
        );

        // 2. Poziv tipa STING
        Call call = new Call(
            4L,
            "Test location",
            LocalDateTime.now(),
            1,
            IncidentType.STING,
            Status.PENDING,
            null,
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
}