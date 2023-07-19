package com.example.Servletdemofull.application.services;

import com.example.Servletdemofull.infrastructure.output.entity.Patient;
import com.example.Servletdemofull.infrastructure.output.repository.PatientRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
public class CreateUpdatePatientService {

    private final PatientRepository repository;
    private final Logger logger;

    public CreateUpdatePatientService(PatientRepository repository) {
        this.repository = repository;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public Patient savePatient(Patient patient) {
        logger.debug("Input parameter patient {}", patient);
        repository.save(patient);

        logger.debug("Output {}", patient);
        return patient;
    }
}
