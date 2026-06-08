package com.ftn.sbnz.service.drools;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;

@Service
public class DroolsMemory {
    
    private final KieSession session;

    public DroolsMemory(KieContainer kieContainer) {
        this.session = kieContainer.newKieSession("skjar");
    }

    public KieSession getSession() {
        return session;
    }

    @PreDestroy
    public void destroy() {
        session.dispose();
    }
}
