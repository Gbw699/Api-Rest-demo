package com.example.Servletdemofull.infrastructure.input.rest.controllers;

import com.example.Servletdemofull.infrastructure.input.rest.dtos.PatientDto;
import com.example.Servletdemofull.infrastructure.input.rest.mappers.PatientMapper;
import com.example.Servletdemofull.infrastructure.output.entity.Patient;
import com.example.Servletdemofull.infrastructure.output.repository.PatientRepository;
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
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final Logger logger;

    public PatientController(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @GetMapping("")
    public ResponseEntity<List<Patient>> getAllPatients() {

        Optional<List<Patient>> patients = Optional.of(patientRepository.findAll());

        logger.debug("Output {}", patients.get().toString());

        if (patients.get().isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity
                .ok()
                .body(patients.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable UUID id) {

        logger.debug("Input parameters id {}", id);

        Optional<Patient> patient = patientRepository.findById(id);

        if (patient.isEmpty()) return ResponseEntity.notFound().build();

        logger.debug("Output {}", patient.get().toString());

        return new ResponseEntity<>(patient.get(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<PatientDto> createPatient(@RequestBody @Valid PatientDto patientDto) {

        Patient patient = patientMapper.fromUserDto(patientDto);
        patientRepository.save(patient);

        logger.debug("Output {}", patientDto.toString());

        return ResponseEntity.ok().body(patientDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatient(@RequestBody @Valid PatientDto patientDto, @PathVariable UUID id) {

        logger.debug("Input parameters id {}, body {}", id, patientDto.toString());

        Optional<Patient> patientToUpdate = patientRepository.findById(id);

        if (patientToUpdate.isEmpty())
            return new ResponseEntity<>("No se encotró el paciente en la BDD", HttpStatus.NOT_FOUND);

        Patient patient = patientToUpdate.get();
        patient = patientMapper.fromUserDto(patientDto);
        patientRepository.save(patient);

        logger.debug("Output {}", patientDto.toString());

        return ResponseEntity.ok().body("El paciente se editó correctamente en la BDD" +
                "");
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteAllPatients() {

        patientRepository.deleteAll();

        return new ResponseEntity<>("Se eliminaron todos los pacientes de la BDD", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatientById(@PathVariable UUID id) {

        logger.debug("Input parameters id {}", id);

        Optional<Patient> user = patientRepository.findById(id);

        if (user.isEmpty()) return new ResponseEntity<>("No se encontró el paciente en la BDD", HttpStatus.NOT_FOUND);

        patientRepository.deleteById(id);

        return new ResponseEntity<>("El paciente se borró exitosamente de la BDD", HttpStatus.OK);
    }
}
