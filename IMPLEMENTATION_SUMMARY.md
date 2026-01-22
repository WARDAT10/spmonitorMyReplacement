# Implementation Summary

## Overview
This document summarizes the implementation of the SP Monitor application with PostgreSQL backend and Flutter frontend.

## Backend Implementation (Spring Boot)

### 1. Database Entities Created
- **Caregiver**: Stores caregiver information (full name, email, password, phone, address, age)
- **Patient**: Stores patient information (full name, gender, date of birth, height, weight, phone)
- **PatientDevice**: Links devices to patients (device ID, device name, patient reference)
- **VitalSigns**: Stores IoT sensor data (glucose, heart rate, temperature, BP, SpO2, location)
- **Alert**: Stores alert notifications (alert type, message, sent via, timestamp)

### 2. Database Views Created
- **patient_bmi**: Automatically calculates BMI from weight and height
- **patient_health_status**: Determines health status (NORMAL, LOW RISK, HIGH RISK) based on:
  - HIGH RISK: Glucose > 11.0 OR Systolic BP ≥ 140 OR Diastolic BP ≥ 90
  - LOW RISK: Glucose < 3.9 OR Systolic BP < 90 OR Diastolic BP < 60
  - NORMAL: All other values
- **patient_health_snapshot**: Complete patient health overview

### 3. API Endpoints
- `POST /api/caregivers/register` - Register new caregiver
- `POST /api/caregivers/login` - Login with email and password
- `GET /api/caregivers/{id}` - Get caregiver details
- `PUT /api/caregivers/{id}/profile` - Update caregiver profile (full name, address, age)

### 4. Features
- JPA/Hibernate for database operations
- Automatic table creation
- Input validation
- CORS configuration for mobile app access
- Session management using SharedPreferences

## Frontend Implementation (Flutter)

### 1. Signup Page Updates
- Added all required fields:
  - Full Name
  - Email
  - Password
  - Confirm Password
  - Phone Number (Optional)
- Password visibility toggle
- Form validation
- Connected to Spring Boot API

### 2. Login Page Updates
- Changed from username to email-based login
- Password visibility toggle
- Connected to Spring Boot API
- Session management

### 3. Profile Page (New)
- Update personal information:
  - Full Name
  - Address
  - Age
- Form validation
- Load existing profile data
- Update profile via API

### 4. Navigation Drawer Updates
- Added Profile icon above Logout button
- Profile icon navigates to ProfilePage
- Logout clears session data

### 5. API Service
- Created `lib/services/api_service.dart`
- Handles all API calls to Spring Boot backend
- Session management with SharedPreferences
- Error handling

## Database Schema

### Caregivers Table
- caregiver_id (Primary Key)
- full_name
- email (Unique)
- password
- phone_number
- address
- age
- created_at
- updated_at

### Patients Table
- patient_id (Primary Key)
- full_name
- gender
- date_of_birth
- height_m
- weight_kg
- phone_number
- caregiver_id (Foreign Key)
- created_at

### Patient Devices Table
- device_id (Primary Key)
- device_name
- patient_id (Foreign Key)
- created_at

### Vital Signs Table
- vital_id (Primary Key)
- patient_id (Foreign Key)
- glucose_mmol
- heart_rate_bpm
- temperature_c
- bp_systolic
- bp_diastolic
- spo2
- latitude
- longitude
- recorded_at

### Alerts Table
- alert_id (Primary Key)
- vital_id (Foreign Key)
- patient_id (Foreign Key)
- alert_type
- message
- sent_via
- sent_at

## Setup Instructions

### Backend
1. Install PostgreSQL
2. Create database: `CREATE DATABASE spmonitor;`
3. Update `application.properties` with database credentials
4. Run: `./mvnw spring-boot:run`

### Frontend
1. Update API base URL in `lib/services/api_service.dart`
2. Run: `flutter pub get`
3. Run: `flutter run`

## Next Steps (Future Enhancements)

1. **Password Hashing**: Implement BCrypt for password security
2. **JWT Authentication**: Add token-based authentication
3. **Patient Management**: Add CRUD operations for patients
4. **Device Management**: Add device registration and management
5. **Vital Signs API**: Add endpoints for IoT devices to post data
6. **Real-time Updates**: Implement WebSocket for real-time data
7. **Alerts System**: Implement automatic alert generation
8. **BMI Calculation**: Already implemented in database view
9. **Health Status**: Already implemented in database view

## Notes

- Passwords are currently stored in plain text (development only)
- CORS is enabled for all origins (update for production)
- Database views are automatically created on application startup
- The application uses JPA auto-ddl for table creation
- Session data is stored locally using SharedPreferences
