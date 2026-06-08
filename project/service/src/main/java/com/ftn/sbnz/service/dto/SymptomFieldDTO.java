package com.ftn.sbnz.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SymptomFieldDTO {
    private String key;
    private String label;
    private String type; // boolean
}