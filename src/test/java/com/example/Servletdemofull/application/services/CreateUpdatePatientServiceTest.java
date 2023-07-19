package com.example.Servletdemofull.application.services;

import com.example.Servletdemofull.infrastructure.output.entity.Patient;
import com.example.Servletdemofull.infrastructure.output.repository.PatientRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CreateUpdatePatientServiceTest {

    CreateUpdatePatientService createUpdatePatientService;
    @Mock
    private PatientRepository repository;

    @BeforeEach
    void setUp() {
        createUpdatePatientService = new CreateUpdatePatientService(repository);
    }

    @Test
    void savePatient() {
        Patient patient = new Patient(UUID.randomUUID(), "Pedro", "Rodriguez", "rodri@gmial.com", "2614539473");

        Patient actual = createUpdatePatientService.savePatient(patient);

        Assertions.assertEquals(patient, actual);
    }
}