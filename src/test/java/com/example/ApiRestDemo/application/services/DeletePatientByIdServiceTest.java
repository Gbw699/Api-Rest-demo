package com.example.ApiRestDemo.application.services;

import com.example.ApiRestDemo.infrastructure.output.repository.PatientRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class DeletePatientByIdServiceTest {

    DeletePatientByIdService deletePatientByIdService;
    @Mock
    private PatientRepository repository;

    @BeforeEach
    void setUp() {
        deletePatientByIdService = new DeletePatientByIdService(repository);
    }

    @Test
    void deleteByIdTest() {
        String expect = "El paciente se borró exitosamente de la BDD";

        String actual = deletePatientByIdService.deleteById(UUID.randomUUID());

        Assertions.assertEquals(expect, actual);
    }
}