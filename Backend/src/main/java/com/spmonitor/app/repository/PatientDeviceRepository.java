package com.spmonitor.app.repository;

import com.spmonitor.app.model.PatientDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PatientDeviceRepository extends JpaRepository<PatientDevice, Long> {
    List<PatientDevice> findByPatient_PatientId(Long patientId);
    
    @Query("SELECT pd FROM PatientDevice pd WHERE pd.patient.patientId = :patientId")
    List<PatientDevice> findAllByPatientId(@Param("patientId") Long patientId);
}
