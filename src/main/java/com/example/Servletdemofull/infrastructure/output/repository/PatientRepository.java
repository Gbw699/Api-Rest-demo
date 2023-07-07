package com.example.Servletdemofull.infrastructure.output.repository;

import com.example.Servletdemofull.infrastructure.output.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
}