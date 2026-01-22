package com.spmonitor.app.repository;

import com.spmonitor.app.model.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {
    Optional<Caregiver> findByEmail(String email);
    boolean existsByEmail(String email);
}
