package com.example.Servletdemofull.application.services;

import com.example.Servletdemofull.infrastructure.output.entity.Patient;
import com.example.Servletdemofull.infrastructure.output.repository.PatientRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GetAllPatientsServiceTest {

    GetAllPatientsService getAllPatientsService;

    @Mock
    private PatientRepository repository;

    @BeforeEach
    void setUp() {
        getAllPatientsService = new GetAllPatientsService(repository);
    }

    @Test
    void getTest() {

        Optional<List<Patient>> patients = Optional.of(repository.findAll());

        Optional<List<Patient>> result = getAllPatientsService.get();

        Assertions.assertEquals(patients, result);
    }
}