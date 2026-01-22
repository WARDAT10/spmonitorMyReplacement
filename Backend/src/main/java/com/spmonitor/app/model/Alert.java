package com.spmonitor.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private Long alertId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vital_id")
    private VitalSigns vitalSigns;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @Size(max = 50)
    @Column(name = "alert_type")
    private String alertType;
    
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
    
    @Size(max = 20)
    @Column(name = "sent_via")
    private String sentVia;
    
    @Column(name = "sent_at", nullable = false, updatable = false)
    private LocalDateTime sentAt;
    
    @PrePersist
    protected void onCreate() {
        sentAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getAlertId() {
        return alertId;
    }
    
    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }
    
    public VitalSigns getVitalSigns() {
        return vitalSigns;
    }
    
    public void setVitalSigns(VitalSigns vitalSigns) {
        this.vitalSigns = vitalSigns;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public String getAlertType() {
        return alertType;
    }
    
    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getSentVia() {
        return sentVia;
    }
    
    public void setSentVia(String sentVia) {
        this.sentVia = sentVia;
    }
    
    public LocalDateTime getSentAt() {
        return sentAt;
    }
    
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
