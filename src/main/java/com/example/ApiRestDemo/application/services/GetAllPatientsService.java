package com.example.ApiRestDemo.application.services;

import com.example.ApiRestDemo.infrastructure.output.entity.Patient;
import com.example.ApiRestDemo.infrastructure.output.repository.PatientRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetAllPatientsService {

    private final PatientRepository repository;
    private final Logger logger;

    public GetAllPatientsService(PatientRepository repository) {
        this.repository = repository;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public Optional<List<Patient>> get()  {

        Optional<List<Patient>> patients = Optional.of(repository.findAll());

        logger.debug("Output {}", patients);
        return patients;
    }
}
