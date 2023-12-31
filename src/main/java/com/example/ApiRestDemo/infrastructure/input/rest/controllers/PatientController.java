package com.example.ApiRestDemo.infrastructure.input.rest.controllers;

import com.example.ApiRestDemo.application.services.CreateUpdatePatientService;
import com.example.ApiRestDemo.application.services.DeletePatientByIdService;
import com.example.ApiRestDemo.application.services.GetPatientByIdService;
import com.example.ApiRestDemo.application.services.DeleteAllService;
import com.example.ApiRestDemo.infrastructure.output.entity.Patient;
import com.example.ApiRestDemo.infrastructure.input.rest.dtos.PatientDto;
import com.example.ApiRestDemo.application.services.GetAllPatientsService;
import com.example.ApiRestDemo.infrastructure.input.rest.mappers.PatientMapper;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/v1/patient")
public class PatientController {

    private final PatientMapper patientMapper;
    private final Logger logger;
    private final GetAllPatientsService getAllPatientsService;
    private final GetPatientByIdService getPatientByIdService;
    private final CreateUpdatePatientService createUpdatePatientService;
    private final DeletePatientByIdService deletePatientByIdService;
    private final DeleteAllService deleteAllService;

    public PatientController(PatientMapper patientMapper,
                             GetAllPatientsService getAllPatientsService,
                             GetPatientByIdService getPatientByIdService,
                             CreateUpdatePatientService createUpdatePatientService,
                             DeletePatientByIdService deletePatientByIdService,
                             DeleteAllService deleteAllService) {
        this.patientMapper = patientMapper;
        this.getAllPatientsService = getAllPatientsService;
        this.getPatientByIdService = getPatientByIdService;
        this.createUpdatePatientService = createUpdatePatientService;
        this.deletePatientByIdService = deletePatientByIdService;
        this.deleteAllService = deleteAllService;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @GetMapping("")
    public ResponseEntity<List<Patient>> getAllPatients() {

        Optional<List<Patient>> patients = getAllPatientsService.get();

        logger.debug("Output {}", patients.get().toString());
        if (patients.get().isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity
                .ok()
                .body(patients.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable UUID id) {
        logger.debug("Input parameters id {}", id);

        Optional<Patient> patient = getPatientByIdService.getById(id);

        if (patient.isEmpty()) return ResponseEntity.notFound().build();

        logger.debug("Output {}", patient.get().toString());
        return new ResponseEntity<>(patient.get(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<PatientDto> createPatient(@RequestBody @Valid PatientDto patientDto) {
        logger.debug("Input parameter patientDto {}", patientDto);

        Patient patient = patientMapper.fromPatientDto(patientDto);
        createUpdatePatientService.savePatient(patient);
        PatientDto patientDto1 = patientMapper.fromPatient(patient);

        logger.debug("Output {}", patientDto1.toString());
        return ResponseEntity.ok().body(patientDto1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatient(@RequestBody @Valid PatientDto patientDto, @PathVariable UUID id) {
        logger.debug("Input parameters id {}, body {}", id, patientDto.toString());

        if (!id.equals(patientDto.getId())) {
            return new ResponseEntity<>("El id de la URL y el id del json no coinciden", HttpStatus.BAD_REQUEST);
        }

        Optional<Patient> patientToUpdate = getPatientByIdService.getById(id);

        if (patientToUpdate.isEmpty())
            return new ResponseEntity<>("No se encotró el paciente en la BDD", HttpStatus.NOT_FOUND);

        Patient patient = patientToUpdate.get();
        patient = patientMapper.fromPatientDto(patientDto);
        createUpdatePatientService.savePatient(patient);

        logger.debug("Output {}", patient.toString());
        return ResponseEntity.ok().body("El paciente se editó correctamente en la BDD" +
                "");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatientById(@PathVariable UUID id) {
        logger.debug("Input parameters id {}", id);

        Optional<Patient> user = getPatientByIdService.getById(id);

        if (user.isEmpty()) return new ResponseEntity<>("No se encontró el paciente en la BDD", HttpStatus.NOT_FOUND);

        String response = deletePatientByIdService.deleteById(id);

        logger.debug("Output {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteAllPatients() {

        Optional<List<Patient>> user = getAllPatientsService.get();

        if (user.get().isEmpty())
            return new ResponseEntity<>("No se encontró ningún paciente en la BDD", HttpStatus.NOT_FOUND);

        String response = deleteAllService.delete();

        logger.debug("Output {}", response);
        return new ResponseEntity<>("Se eliminaron todos los pacientes de la BDD", HttpStatus.OK);
    }
}
