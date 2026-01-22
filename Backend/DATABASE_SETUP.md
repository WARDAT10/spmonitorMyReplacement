# Database Setup Guide

## PostgreSQL Database Configuration

### 1. Install PostgreSQL
Make sure PostgreSQL is installed on your system.

### 2. Create Database
```sql
CREATE DATABASE spmonitor;
```

### 3. Update application.properties
Update the database connection details in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/spmonitor
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. Run the Application
The application will automatically:
- Create all tables using JPA/Hibernate
- Create the health_status enum
- Create views (patient_bmi, patient_health_status, patient_health_snapshot)

### 5. Manual View Creation (Optional)
If you want to create views manually, run the SQL script:
```bash
psql -U postgres -d spmonitor -f src/main/resources/db/migration/V1__init_database.sql
```

## Database Schema

### Tables
- **caregivers**: Stores caregiver information
- **patients**: Stores patient information
- **patient_devices**: Links devices to patients
- **vital_signs**: Stores IoT sensor readings
- **alerts**: Stores alert notifications

### Views
- **patient_bmi**: Calculates BMI from weight and height
- **patient_health_status**: Determines health status (NORMAL, LOW RISK, HIGH RISK)
- **patient_health_snapshot**: Complete patient health overview

### Health Status Logic
- **HIGH RISK**: Glucose > 11.0 OR Systolic BP ≥ 140 OR Diastolic BP ≥ 90
- **LOW RISK**: Glucose < 3.9 OR Systolic BP < 90 OR Diastolic BP < 60
- **NORMAL**: All other values

## API Endpoints

### Caregiver Endpoints
- `POST /api/caregivers/register` - Register new caregiver
- `POST /api/caregivers/login` - Login caregiver
- `GET /api/caregivers/{id}` - Get caregiver details
- `PUT /api/caregivers/{id}/profile` - Update caregiver profile

## Notes
- Passwords are stored in plain text (for development). In production, use password hashing (BCrypt).
- The application runs on port 8080 by default.
- CORS is enabled for all origins (update for production).
