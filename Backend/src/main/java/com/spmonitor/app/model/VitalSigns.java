package com.spmonitor.app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vital_signs")
public class VitalSigns {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vital_id")
    private Long vitalId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @Column(name = "glucose_mmol", precision = 5, scale = 2)
    private BigDecimal glucoseMmol;
    
    @Column(name = "heart_rate_bpm")
    private Integer heartRateBpm;
    
    @Column(name = "temperature_c", precision = 4, scale = 2)
    private BigDecimal temperatureC;
    
    @Column(name = "bp_systolic")
    private Integer bpSystolic;
    
    @Column(name = "bp_diastolic")
    private Integer bpDiastolic;
    
    @Column(name = "spo2")
    private Integer spo2;
    
    @Column(name = "latitude", precision = 9, scale = 6)
    private BigDecimal latitude;
    
    @Column(name = "longitude", precision = 9, scale = 6)
    private BigDecimal longitude;
    
    @Column(name = "recorded_at", nullable = false, updatable = false)
    private LocalDateTime recordedAt;
    
    @PrePersist
    protected void onCreate() {
        recordedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getVitalId() {
        return vitalId;
    }
    
    public void setVitalId(Long vitalId) {
        this.vitalId = vitalId;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public BigDecimal getGlucoseMmol() {
        return glucoseMmol;
    }
    
    public void setGlucoseMmol(BigDecimal glucoseMmol) {
        this.glucoseMmol = glucoseMmol;
    }
    
    public Integer getHeartRateBpm() {
        return heartRateBpm;
    }
    
    public void setHeartRateBpm(Integer heartRateBpm) {
        this.heartRateBpm = heartRateBpm;
    }
    
    public BigDecimal getTemperatureC() {
        return temperatureC;
    }
    
    public void setTemperatureC(BigDecimal temperatureC) {
        this.temperatureC = temperatureC;
    }
    
    public Integer getBpSystolic() {
        return bpSystolic;
    }
    
    public void setBpSystolic(Integer bpSystolic) {
        this.bpSystolic = bpSystolic;
    }
    
    public Integer getBpDiastolic() {
        return bpDiastolic;
    }
    
    public void setBpDiastolic(Integer bpDiastolic) {
        this.bpDiastolic = bpDiastolic;
    }
    
    public Integer getSpo2() {
        return spo2;
    }
    
    public void setSpo2(Integer spo2) {
        this.spo2 = spo2;
    }
    
    public BigDecimal getLatitude() {
        return latitude;
    }
    
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    
    public BigDecimal getLongitude() {
        return longitude;
    }
    
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    
    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }
    
    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}
