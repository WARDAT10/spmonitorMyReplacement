package com.spmonitor.app.repository;

import com.spmonitor.app.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByPatient_PatientIdOrderBySentAtDesc(Long patientId);
    
    @Query("SELECT a FROM Alert a WHERE a.patient.patientId = :patientId ORDER BY a.sentAt DESC")
    List<Alert> findAllByPatientIdOrderBySentAtDesc(@Param("patientId") Long patientId);
}
