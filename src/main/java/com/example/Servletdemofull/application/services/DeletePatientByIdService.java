package com.example.Servletdemofull.application.services;

import com.example.Servletdemofull.infrastructure.output.repository.PatientRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletePatientByIdService {

    private final PatientRepository repository;
    private final Logger logger;

    public DeletePatientByIdService (PatientRepository repository) {
        this.repository = repository;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public String deleteById(UUID id) {
        logger.debug("Input parameter id {}", id);

        repository.deleteById(id);

        return "El paciente se borró exitosamente de la BDD";
    }
}
