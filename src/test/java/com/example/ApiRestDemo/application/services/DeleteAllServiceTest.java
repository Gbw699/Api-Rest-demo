package com.example.ApiRestDemo.application.services;

import com.example.ApiRestDemo.infrastructure.output.repository.PatientRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteAllServiceTest {

    DeleteAllService deleteAllService;
    @Mock
    PatientRepository repository;

    @BeforeEach
    void setUp() {
        deleteAllService = new DeleteAllService(repository);
    }

    @Test
    void delete() {
        String expect = "Se eliminaron todos los pacientes de la BDD";

        String actual = deleteAllService.delete();

        Assertions.assertEquals(expect, actual);
    }
}