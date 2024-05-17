package com.agsr.task.repository;

import com.agsr.task.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, String> {
}
