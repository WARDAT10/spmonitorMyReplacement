package com.spmonitor.app.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VitalSummaryDTO {
    private BigDecimal glucoseMmol;
    private Integer heartRateBpm;
    private BigDecimal temperatureC;
    private Integer bpSystolic;
    private Integer bpDiastolic;
    private Integer spo2;
    private LocalDateTime recordedAt;

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

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}

