package com.example.Servletdemofull.application.services;

import com.example.Servletdemofull.infrastructure.output.entity.Patient;
import com.example.Servletdemofull.infrastructure.output.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetPatientByIdService {

    private final PatientRepository repository;
    private final Logger logger;

    public GetPatientByIdService(PatientRepository repository) {
        this.repository = repository;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public Optional<Patient> getById(UUID id) {
        logger.debug("Input parameter id {}", id);

        Optional<Patient> patient = repository.findById(id);

        logger.debug("Output {}", patient);
        return patient;
    }
}
