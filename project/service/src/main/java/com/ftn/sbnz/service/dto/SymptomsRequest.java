package com.ftn.sbnz.service.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SymptomsRequest {
    private Long callId;
    private String incidentType;
    private Map<String, Boolean> symptoms;

}
