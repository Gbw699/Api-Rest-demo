package com.example.ApiRestDemo.application.services;

import com.example.ApiRestDemo.infrastructure.output.entity.Patient;
import com.example.ApiRestDemo.infrastructure.output.repository.PatientRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class GetPatientByIdServiceTest {

    GetPatientByIdService getPatientByIdService;
    @Mock
    private PatientRepository repository;

    @BeforeEach
    void setUp() {
        getPatientByIdService = new GetPatientByIdService(repository);
    }

    @Test
    void getByIdTest() {
        UUID id = UUID.randomUUID();

        Optional<Patient> expected = repository.findById(id);

        Optional<Patient> actual = getPatientByIdService.getById(UUID.randomUUID());

        Assertions.assertEquals(expected, actual);
    }
}