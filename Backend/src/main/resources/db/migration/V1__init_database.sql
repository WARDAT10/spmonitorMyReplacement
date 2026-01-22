-- Create Health Status Enum
CREATE TYPE health_status AS ENUM (
    'NORMAL',
    'LOW RISK',
    'HIGH RISK'
);

-- Create Caregivers Table (if not exists, JPA will handle it but we define structure)
-- Note: JPA will auto-create tables, but we define views here

-- Create Patient BMI View
CREATE OR REPLACE VIEW patient_bmi AS
SELECT
    p.patient_id,
    p.full_name,
    ROUND(
        (p.weight_kg / (p.height_m * p.height_m))::numeric, 2
    ) AS bmi
FROM patients p
WHERE p.height_m IS NOT NULL AND p.weight_kg IS NOT NULL AND p.height_m > 0;

-- Create Patient Health Status View
CREATE OR REPLACE VIEW patient_health_status AS
SELECT
    v.vital_id,
    v.patient_id,
    v.glucose_mmol,
    v.bp_systolic,
    v.bp_diastolic,
    CASE
        WHEN v.glucose_mmol > 11
             OR v.bp_systolic >= 140
             OR v.bp_diastolic >= 90
        THEN 'HIGH RISK'::health_status
        WHEN v.glucose_mmol < 3.9
             OR v.bp_systolic < 90
             OR v.bp_diastolic < 60
        THEN 'LOW RISK'::health_status
        ELSE 'NORMAL'::health_status
    END AS status,
    v.recorded_at
FROM vital_signs v;

-- Create Full Patient Health Snapshot View
CREATE OR REPLACE VIEW patient_health_snapshot AS
SELECT
    p.patient_id,
    p.full_name,
    p.gender,
    p.date_of_birth,
    p.height_m,
    p.weight_kg,
    b.bmi,
    v.glucose_mmol,
    v.heart_rate_bpm,
    v.temperature_c,
    v.bp_systolic,
    v.bp_diastolic,
    v.bp_systolic || '/' || v.bp_diastolic AS blood_pressure,
    v.spo2,
    s.status,
    v.recorded_at
FROM patients p
LEFT JOIN patient_bmi b ON p.patient_id = b.patient_id
LEFT JOIN vital_signs v ON p.patient_id = v.patient_id
LEFT JOIN patient_health_status s ON v.vital_id = s.vital_id
ORDER BY v.recorded_at DESC;
