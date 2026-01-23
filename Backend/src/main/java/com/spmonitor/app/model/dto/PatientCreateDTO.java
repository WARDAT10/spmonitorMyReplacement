package com.spmonitor.app.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class PatientCreateDTO {
    @NotBlank(message = "Full name is required")
    @Size(max = 100)
    private String fullName;

    @Min(value = 0, message = "Age must be positive")
    private Integer age;

    @Size(max = 10)
    private String gender;

    @NotNull(message = "Weight is required")
    private BigDecimal weightKg;

    @NotNull(message = "Height is required")
    private BigDecimal heightCm;

    @Size(max = 20)
    private String phoneNumber;

    // Initial vital signs (optional but supported by the current Flutter form)
    private BigDecimal glucoseMmol;
    private BigDecimal temperatureC;
    private Integer heartRateBpm;
    private Integer spo2;

    // Device binding
    private String deviceName;
    private String deviceExternalId;

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

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public BigDecimal getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(BigDecimal heightCm) {
        this.heightCm = heightCm;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getGlucoseMmol() {
        return glucoseMmol;
    }

    public void setGlucoseMmol(BigDecimal glucoseMmol) {
        this.glucoseMmol = glucoseMmol;
    }

    public BigDecimal getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(BigDecimal temperatureC) {
        this.temperatureC = temperatureC;
    }

    public Integer getHeartRateBpm() {
        return heartRateBpm;
    }

    public void setHeartRateBpm(Integer heartRateBpm) {
        this.heartRateBpm = heartRateBpm;
    }

    public Integer getSpo2() {
        return spo2;
    }

    public void setSpo2(Integer spo2) {
        this.spo2 = spo2;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceExternalId() {
        return deviceExternalId;
    }

    public void setDeviceExternalId(String deviceExternalId) {
        this.deviceExternalId = deviceExternalId;
    }
}

