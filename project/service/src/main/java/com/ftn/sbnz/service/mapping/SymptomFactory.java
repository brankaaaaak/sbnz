package com.ftn.sbnz.service.mapping;

import org.springframework.stereotype.Component;

import com.ftn.sbnz.model.symptoms.FaintingSymptoms;
import com.ftn.sbnz.model.symptoms.InjuryExtremitySymptoms;
import com.ftn.sbnz.model.symptoms.InjuryHeadSymptoms;
import com.ftn.sbnz.model.symptoms.StingSymptoms;
import com.ftn.sbnz.model.symptoms.SymptomType;

import java.util.List;
import java.util.Map;

@Component
public class SymptomFactory {

    private final List<Class<?>> symptomClasses = List.of(
        com.ftn.sbnz.model.symptoms.InjuryHeadSymptoms.class,
        com.ftn.sbnz.model.symptoms.InjuryExtremitySymptoms.class,
        com.ftn.sbnz.model.symptoms.StingSymptoms.class,
        com.ftn.sbnz.model.symptoms.FaintingSymptoms.class
    );

    public Object create(String incidentType, Long callId, Map<String, Boolean> symptoms) {

        try {
            for (Class<?> clazz : symptomClasses) {

                SymptomType annotation = clazz.getAnnotation(SymptomType.class);

                if (annotation == null) continue;

                if (!annotation.value().equals(incidentType)) continue;

                return buildObject(clazz, callId, symptoms);
            }

            throw new RuntimeException("Unknown symptom type: " + incidentType);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object buildObject(Class<?> clazz, Long callId, Map<String, Boolean> symptoms) throws Exception {

        if (clazz == com.ftn.sbnz.model.symptoms.InjuryHeadSymptoms.class) {
            return new InjuryHeadSymptoms(
                callId,
                symptoms.get("vomiting"),
                symptoms.get("openWound")
            );
        }

        if (clazz == com.ftn.sbnz.model.symptoms.InjuryExtremitySymptoms.class) {
            return new InjuryExtremitySymptoms(
                callId,
                symptoms.get("openFracture"),
                symptoms.get("bleeding")
            );
        }

        if (clazz == com.ftn.sbnz.model.symptoms.StingSymptoms.class) {
            return new StingSymptoms(
                callId,
                symptoms.get("choking"),
                symptoms.get("systemicSwelling"),
                symptoms.get("skinReaction"),
                symptoms.get("previousSevereReaction")
            );
        }

        if (clazz == com.ftn.sbnz.model.symptoms.FaintingSymptoms.class) {
            return new FaintingSymptoms(
                callId,
                symptoms.get("diabetes"),
                symptoms.get("tookTherapy"),
                symptoms.get("painEarlier")
            );
        }

        throw new RuntimeException("Unsupported class: " + clazz);
    }
}