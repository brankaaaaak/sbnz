package com.ftn.sbnz.service.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.assessment.PatientAssessment;
import com.ftn.sbnz.model.enums.IncidentType;
import com.ftn.sbnz.model.enums.Status;
import com.ftn.sbnz.model.events.CallReceivedEvent;
import com.ftn.sbnz.model.events.FinishCallEvent;
import com.ftn.sbnz.model.models.Call;
import com.ftn.sbnz.model.stats.SystemStats;
import com.ftn.sbnz.service.drools.DroolsMemory;
import com.ftn.sbnz.service.dto.CallDTO;
import com.ftn.sbnz.service.dto.FinalAssessmentResponse;
import com.ftn.sbnz.service.dto.PreliminaryAssessmentResponse;
import com.ftn.sbnz.service.dto.SymptomFieldDTO;
import com.ftn.sbnz.service.dto.SymptomsRequest;
import com.ftn.sbnz.service.dto.SystemStatsDTO;
import com.ftn.sbnz.service.mapping.CallMapper;
import com.ftn.sbnz.service.mapping.SymptomFactory;

@Service
public class EmergencyService {

        private final DroolsMemory droolsMemory;
        private final SymptomFactory symptomFactory;
        private final CallMapper callMapper;

        public EmergencyService(
                        DroolsMemory droolsMemory,
                        SymptomFactory symptomFactory,
                        CallMapper callMapper) {

                this.droolsMemory = droolsMemory;
                this.symptomFactory = symptomFactory;
                this.callMapper = callMapper;
        }

        public List<CallDTO> getAllCalls() {

                KieSession session = droolsMemory.getSession();

                synchronized (session) {

                        Map<Long, PatientAssessment> assessmentMap = session.getObjects()
                                        .stream()
                                        .filter(PatientAssessment.class::isInstance)
                                        .map(PatientAssessment.class::cast)
                                        .collect(Collectors.toMap(
                                                        PatientAssessment::getCallId,
                                                        a -> a));

                        return session.getObjects()
                                        .stream()
                                        .filter(Call.class::isInstance)
                                        .map(Call.class::cast)
                                        .map(call -> callMapper.toDTO(
                                                        call,
                                                        assessmentMap.get(call.getId())))
                                        .toList();
                }
        }

        public SystemStatsDTO getSystemStats() {

                KieSession session = droolsMemory.getSession();

                synchronized (session) {

                        SystemStats stats = session.getObjects()
                                        .stream()
                                        .filter(SystemStats.class::isInstance)
                                        .map(SystemStats.class::cast)
                                        .findFirst()
                                        .orElse(new SystemStats());

                        return new SystemStatsDTO(
                                        stats.getActiveCallCount(),
                                        stats.getRedLevelCount());
                }
        }

        public PreliminaryAssessmentResponse calculatePreliminary(Call call) {

                KieSession session = droolsMemory.getSession();

                synchronized (session) {

                        call.setId(System.currentTimeMillis());
                        call.setCallTime(LocalDateTime.now());
                        call.setStatus(Status.IN_PROGRESS);

                        session.insert(call);

                        session.insert(new CallReceivedEvent(
                                call.getId(),
                                System.currentTimeMillis()
                        ));
                        session.fireAllRules();

                        PatientAssessment assessment = session.getObjects()
                                        .stream()
                                        .filter(PatientAssessment.class::isInstance)
                                        .map(PatientAssessment.class::cast)
                                        .filter(a -> call.getId().equals(a.getCallId()))
                                        .findFirst()
                                        .orElseThrow();

                        List<String> reasons = new ArrayList<>();

                        if (assessment.isElevatedPulse())
                                reasons.add("Elevated pulse");

                        if (assessment.isElevatedTemperature())
                                reasons.add("Elevated temperature");

                        if (assessment.isIrregularBreathing())
                                reasons.add("Irregular breathing");

                        if (assessment.getBloodPressure() != null)
                                reasons.add("Blood pressure: " + assessment.getBloodPressure());

                        if (assessment.getConsciousnessLevel() != null)
                                reasons.add("Consciousness: " + assessment.getConsciousnessLevel());

                        PreliminaryAssessmentResponse response = new PreliminaryAssessmentResponse();

                        response.setCallId(call.getId());
                        response.setIncidentType(call.getIncidentType());
                        response.setPreliminaryLevel(
                                        assessment.getPreliminaryLevel());
                        response.setReasons(reasons);

                        return response;
                }
        }

        public FinalAssessmentResponse calculateFinal(
                        SymptomsRequest request) {

                KieSession session = droolsMemory.getSession();

                synchronized (session) {

                        Long callId = request.getCallId();

                        Call call = session.getObjects()
                                        .stream()
                                        .filter(Call.class::isInstance)
                                        .map(Call.class::cast)
                                        .filter(c -> callId.equals(c.getId()))
                                        .findFirst()
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Call not found: " + callId));

                        PatientAssessment assessment = session.getObjects()
                                        .stream()
                                        .filter(PatientAssessment.class::isInstance)
                                        .map(PatientAssessment.class::cast)
                                        .filter(a -> callId.equals(a.getCallId()))
                                        .findFirst()
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Assessment not found"));

                        assessment.setSymptomsEntered(true);

                        session.update(
                                        session.getFactHandle(assessment),
                                        assessment);

                        Object symptoms = symptomFactory.create(
                                        call.getIncidentType().name(),
                                        callId,
                                        request.getSymptoms());

                        session.insert(symptoms);

                        session.fireAllRules();

                        FinalAssessmentResponse response = new FinalAssessmentResponse();

                        response.setCallId(callId);
                        response.setFinalLevel(
                                        assessment.getFinalLevel());

                        return response;
                }
        }

        public List<SymptomFieldDTO> getSchema(
                        IncidentType type) {

                return switch (type) {

                        case INJURY_HEAD -> List.of(
                                        new SymptomFieldDTO(
                                                        "vomiting",
                                                        "Vomiting",
                                                        "boolean"),
                                        new SymptomFieldDTO(
                                                        "openWound",
                                                        "Open wound",
                                                        "boolean"));

                        case INJURY_EXTREMITY -> List.of(
                                        new SymptomFieldDTO(
                                                        "openFracture",
                                                        "Open fracture",
                                                        "boolean"),
                                        new SymptomFieldDTO(
                                                        "bleeding",
                                                        "Bleeding",
                                                        "boolean"));

                        case STING -> List.of(
                                        new SymptomFieldDTO(
                                                        "choking",
                                                        "Choking",
                                                        "boolean"),
                                        new SymptomFieldDTO(
                                                        "systemicSwelling",
                                                        "Systemic swelling",
                                                        "boolean"),
                                        new SymptomFieldDTO(
                                                        "skinReaction",
                                                        "Skin reaction",
                                                        "boolean"),
                                        new SymptomFieldDTO(
                                                        "previousSevereReaction",
                                                        "Previous severe reaction",
                                                        "boolean"));

                        case FAINTING -> List.of(
                                        new SymptomFieldDTO(
                                                        "diabetes",
                                                        "Diabetes",
                                                        "boolean"),
                                        new SymptomFieldDTO(
                                                        "tookTherapy",
                                                        "Took therapy",
                                                        "boolean"),
                                        new SymptomFieldDTO(
                                                        "painEarlier",
                                                        "Pain earlier",
                                                        "boolean"));
                };
        }

        public void finishCall(Long callId) {

                KieSession session = droolsMemory.getSession();

                synchronized (session) {

                        session.insert(new FinishCallEvent(callId));

                        session.fireAllRules();
                }
        }
}