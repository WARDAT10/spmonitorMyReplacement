package com.spmonitor.app.service;

import com.spmonitor.app.model.Caregiver;
import com.spmonitor.app.model.Patient;
import com.spmonitor.app.model.VitalSigns;
import com.spmonitor.app.model.PatientDevice;
import com.spmonitor.app.model.dto.PatientCreateDTO;
import com.spmonitor.app.model.dto.PatientSummaryDTO;
import com.spmonitor.app.model.dto.VitalSummaryDTO;
import com.spmonitor.app.repository.CaregiverRepository;
import com.spmonitor.app.repository.PatientRepository;
import com.spmonitor.app.repository.VitalSignsRepository;
import com.spmonitor.app.repository.PatientDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CaregiverRepository caregiverRepository;

    @Autowired
    private VitalSignsRepository vitalSignsRepository;

    @Autowired
    private PatientDeviceRepository patientDeviceRepository;

    public Patient createPatient(Long caregiverId, PatientCreateDTO dto) {
        Caregiver caregiver = caregiverRepository.findById(caregiverId)
                .orElseThrow(() -> new IllegalArgumentException("Caregiver not found"));

        Patient patient = new Patient();
        patient.setCaregiver(caregiver);
        patient.setFullName(dto.getFullName());
        patient.setGender(dto.getGender());
        patient.setPhoneNumber(dto.getPhoneNumber());
        patient.setWeightKg(dto.getWeightKg());

        // Height stored in meters, Flutter form currently captures centimeters.
        if (dto.getHeightCm() != null) {
            BigDecimal heightM = dto.getHeightCm()
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            patient.setHeightM(heightM);
        }

        // Flutter form captures age; we approximate DoB as Jan 1st of (currentYear - age).
        if (dto.getAge() != null) {
            int year = LocalDate.now().getYear() - dto.getAge();
            patient.setDateOfBirth(LocalDate.of(year, 1, 1));
        }

        Patient saved = patientRepository.save(patient);

        // Link patient to device if provided
        if (dto.getDeviceName() != null && !dto.getDeviceName().isBlank()
                && dto.getDeviceExternalId() != null && !dto.getDeviceExternalId().isBlank()) {
            PatientDevice device = new PatientDevice();
            device.setPatient(saved);
            device.setDeviceName(dto.getDeviceName().trim());
            device.setExternalId(dto.getDeviceExternalId().trim());
            patientDeviceRepository.save(device);
        }

        // Save initial vitals if provided
        if (dto.getGlucoseMmol() != null ||
                dto.getTemperatureC() != null ||
                dto.getHeartRateBpm() != null ||
                dto.getSpo2() != null) {
            VitalSigns v = new VitalSigns();
            v.setPatient(saved);
            v.setGlucoseMmol(dto.getGlucoseMmol());
            v.setTemperatureC(dto.getTemperatureC());
            v.setHeartRateBpm(dto.getHeartRateBpm());
            v.setSpo2(dto.getSpo2());
            vitalSignsRepository.save(v);
        }

        return saved;
    }

    public List<PatientSummaryDTO> listPatients(Long caregiverId) {
        List<Patient> patients = patientRepository.findByCaregiver_CaregiverId(caregiverId);

        return patients.stream()
                .map(this::toSummary)
                .sorted(Comparator.comparing(PatientSummaryDTO::getPatientId))
                .toList();
    }

    private PatientSummaryDTO toSummary(Patient p) {
        PatientSummaryDTO dto = new PatientSummaryDTO();
        dto.setPatientId(p.getPatientId());
        dto.setFullName(p.getFullName());
        dto.setAge(p.getAge());
        dto.setGender(p.getGender());
        dto.setHeightM(p.getHeightM());
        dto.setWeightKg(p.getWeightKg());
        dto.setBmi(calculateBmi(p.getWeightKg(), p.getHeightM()));

        Optional<VitalSigns> latestOpt = vitalSignsRepository.findTopByPatient_PatientIdOrderByRecordedAtDesc(p.getPatientId());
        if (latestOpt.isPresent()) {
            VitalSigns latest = latestOpt.get();
            VitalSummaryDTO vital = new VitalSummaryDTO();
            vital.setGlucoseMmol(latest.getGlucoseMmol());
            vital.setTemperatureC(latest.getTemperatureC());
            vital.setHeartRateBpm(latest.getHeartRateBpm());
            vital.setSpo2(latest.getSpo2());
            vital.setBpSystolic(latest.getBpSystolic());
            vital.setBpDiastolic(latest.getBpDiastolic());
            vital.setRecordedAt(latest.getRecordedAt());
            dto.setLatestVital(vital);
            dto.setStatus(classifyStatus(vital));
        } else {
            dto.setLatestVital(null);
            dto.setStatus("NORMAL");
        }

        return dto;
    }

    private BigDecimal calculateBmi(BigDecimal weightKg, BigDecimal heightM) {
        if (weightKg == null || heightM == null) return null;
        if (heightM.compareTo(BigDecimal.ZERO) <= 0) return null;
        BigDecimal bmi = weightKg.divide(heightM.multiply(heightM), 2, RoundingMode.HALF_UP);
        return bmi;
    }

    private String classifyStatus(VitalSummaryDTO v) {
        // Mirrors the view logic in V1__init_database.sql
        if (v == null) return "NORMAL";

        if (v.getGlucoseMmol() != null && v.getGlucoseMmol().compareTo(BigDecimal.valueOf(11)) > 0) {
            return "HIGH RISK";
        }
        if (v.getBpSystolic() != null && v.getBpSystolic() >= 140) {
            return "HIGH RISK";
        }
        if (v.getBpDiastolic() != null && v.getBpDiastolic() >= 90) {
            return "HIGH RISK";
        }

        if (v.getGlucoseMmol() != null && v.getGlucoseMmol().compareTo(BigDecimal.valueOf(3.9)) < 0) {
            return "LOW RISK";
        }
        if (v.getBpSystolic() != null && v.getBpSystolic() < 90) {
            return "LOW RISK";
        }
        if (v.getBpDiastolic() != null && v.getBpDiastolic() < 60) {
            return "LOW RISK";
        }

        return "NORMAL";
    }
}

