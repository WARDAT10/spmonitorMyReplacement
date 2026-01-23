package com.spmonitor.app.model.dto;

import java.math.BigDecimal;

public class PatientSummaryDTO {
    private Long patientId;
    private String fullName;
    private Integer age;
    private String gender;
    private BigDecimal heightM;
    private BigDecimal weightKg;
    private BigDecimal bmi;
    private String status; // NORMAL / LOW RISK / HIGH RISK
    private VitalSummaryDTO latestVital;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public BigDecimal getHeightM() {
        return heightM;
    }

    public void setHeightM(BigDecimal heightM) {
        this.heightM = heightM;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public BigDecimal getBmi() {
        return bmi;
    }

    public void setBmi(BigDecimal bmi) {
        this.bmi = bmi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public VitalSummaryDTO getLatestVital() {
        return latestVital;
    }

    public void setLatestVital(VitalSummaryDTO latestVital) {
        this.latestVital = latestVital;
    }
}

