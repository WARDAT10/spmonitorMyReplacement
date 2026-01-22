package com.spmonitor.app.service;

import com.spmonitor.app.model.Caregiver;
import com.spmonitor.app.model.dto.CaregiverProfileDTO;
import com.spmonitor.app.model.dto.CaregiverRegistrationDTO;
import com.spmonitor.app.model.dto.LoginDTO;
import com.spmonitor.app.repository.CaregiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CaregiverService {
    
    @Autowired
    private CaregiverRepository caregiverRepository;
    
    public Caregiver register(CaregiverRegistrationDTO registrationDTO) {
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        
        if (caregiverRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        Caregiver caregiver = new Caregiver();
        caregiver.setFullName(registrationDTO.getFullName());
        caregiver.setEmail(registrationDTO.getEmail());
        caregiver.setPassword(registrationDTO.getPassword()); // In production, hash this password
        caregiver.setPhoneNumber(registrationDTO.getPhoneNumber());
        
        return caregiverRepository.save(caregiver);
    }
    
    public Optional<Caregiver> login(LoginDTO loginDTO) {
        Optional<Caregiver> caregiver = caregiverRepository.findByEmail(loginDTO.getEmail());
        if (caregiver.isPresent() && caregiver.get().getPassword().equals(loginDTO.getPassword())) {
            return caregiver;
        }
        return Optional.empty();
    }
    
    public Optional<Caregiver> getCaregiverById(Long caregiverId) {
        return caregiverRepository.findById(caregiverId);
    }
    
    public Optional<Caregiver> getCaregiverByEmail(String email) {
        return caregiverRepository.findByEmail(email);
    }
    
    public Caregiver updateProfile(Long caregiverId, CaregiverProfileDTO profileDTO) {
        Caregiver caregiver = caregiverRepository.findById(caregiverId)
            .orElseThrow(() -> new IllegalArgumentException("Caregiver not found"));
        
        if (profileDTO.getFullName() != null && !profileDTO.getFullName().isEmpty()) {
            caregiver.setFullName(profileDTO.getFullName());
        }
        if (profileDTO.getAddress() != null) {
            caregiver.setAddress(profileDTO.getAddress());
        }
        if (profileDTO.getAge() != null) {
            caregiver.setAge(profileDTO.getAge());
        }
        
        return caregiverRepository.save(caregiver);
    }
}
