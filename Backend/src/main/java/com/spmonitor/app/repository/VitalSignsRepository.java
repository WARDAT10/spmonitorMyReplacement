package com.spmonitor.app.repository;

import com.spmonitor.app.model.VitalSigns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VitalSignsRepository extends JpaRepository<VitalSigns, Long> {
    List<VitalSigns> findByPatient_PatientIdOrderByRecordedAtDesc(Long patientId);
    
    @Query("SELECT v FROM VitalSigns v WHERE v.patient.patientId = :patientId ORDER BY v.recordedAt DESC")
    List<VitalSigns> findAllByPatientIdOrderByRecordedAtDesc(@Param("patientId") Long patientId);
}
