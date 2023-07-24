package com.example.ApiRestDemo.application.services;

import com.example.ApiRestDemo.infrastructure.output.repository.PatientRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
public class DeleteAllService {

    private final PatientRepository repository;
    private final Logger logger;

    public DeleteAllService(PatientRepository repository) {
        this.repository = repository;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public String delete() {
        repository.deleteAll();

        String response = "Se eliminaron todos los pacientes de la BDD";

        logger.debug("Output {}", response);
        return response;
    }
}
