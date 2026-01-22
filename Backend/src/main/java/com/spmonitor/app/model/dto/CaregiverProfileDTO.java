package com.spmonitor.app.model.dto;

import jakarta.validation.constraints.Size;

public class CaregiverProfileDTO {
    
    @Size(max = 100)
    private String fullName;
    
    @Size(max = 200)
    private String address;
    
    private Integer age;
    
    // Getters and Setters
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
}
