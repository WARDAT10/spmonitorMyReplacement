package com.spmonitor.app.controller;

import com.spmonitor.app.model.Caregiver;
import com.spmonitor.app.model.dto.CaregiverProfileDTO;
import com.spmonitor.app.model.dto.CaregiverRegistrationDTO;
import com.spmonitor.app.model.dto.LoginDTO;
import com.spmonitor.app.service.CaregiverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/caregivers")
@CrossOrigin(origins = "*")
public class CaregiverController {
    
    @Autowired
    private CaregiverService caregiverService;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CaregiverRegistrationDTO registrationDTO) {
        try {
            Caregiver caregiver = caregiverService.register(registrationDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Registration successful");
            response.put("caregiverId", caregiver.getCaregiverId());
            response.put("email", caregiver.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        Optional<Caregiver> caregiver = caregiverService.login(loginDTO);
        if (caregiver.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("caregiverId", caregiver.get().getCaregiverId());
            response.put("email", caregiver.get().getEmail());
            response.put("fullName", caregiver.get().getFullName());
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    
    @GetMapping("/{caregiverId}")
    public ResponseEntity<?> getCaregiver(@PathVariable Long caregiverId) {
        Optional<Caregiver> caregiver = caregiverService.getCaregiverById(caregiverId);
        if (caregiver.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("caregiver", caregiver.get());
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Caregiver not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    @PutMapping("/{caregiverId}/profile")
    public ResponseEntity<?> updateProfile(
            @PathVariable Long caregiverId,
            @Valid @RequestBody CaregiverProfileDTO profileDTO) {
        try {
            Caregiver caregiver = caregiverService.updateProfile(caregiverId, profileDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Profile updated successfully");
            response.put("caregiver", caregiver);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
