package com.spmonitor.app.controller;

import com.spmonitor.app.model.Patient;
import com.spmonitor.app.model.dto.PatientCreateDTO;
import com.spmonitor.app.model.dto.PatientSummaryDTO;
import com.spmonitor.app.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/caregivers/{caregiverId}/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<?> listPatients(@PathVariable Long caregiverId) {
        List<PatientSummaryDTO> patients = patientService.listPatients(caregiverId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("patients", patients);
        response.put("total", patients.size());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createPatient(
            @PathVariable Long caregiverId,
            @Valid @RequestBody PatientCreateDTO dto
    ) {
        try {
            Patient created = patientService.createPatient(caregiverId, dto);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Patient created");
            response.put("patientId", created.getPatientId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}

