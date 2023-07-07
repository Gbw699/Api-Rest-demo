package com.example.Servletdemofull.infrastructure.input.rest.controllers;

import com.example.Servletdemofull.config.JWT.JwtService;
import com.example.Servletdemofull.domain.user.RoleEnum;
import com.example.Servletdemofull.infrastructure.input.rest.dtos.AuthenticationResponseDto;
import com.example.Servletdemofull.infrastructure.output.entity.Patient;
import com.example.Servletdemofull.infrastructure.input.rest.mappers.PatientMapper;
import com.example.Servletdemofull.infrastructure.output.entity.User;
import com.example.Servletdemofull.infrastructure.output.repository.PatientRepository;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private PatientMapper patientMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser
    void getAllPatients() throws Exception {
        //given
        List<Patient> patients = new ArrayList<>(
                Arrays.asList(
                        new Patient(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489"),
                        new Patient(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489"),
                        new Patient(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489")
                )
        );

        User mock = new User(null, "Juan", "Pedrera", "pedrera@gmail.com", "admin", RoleEnum.ADMIN);

        //expectation
        when(patientRepository.findAll()).thenReturn(patients);

        //performs
        mockMvc.perform(get("/api/v1/patient")
                        .header("Authorization", "Bearer " + generateTestToken(mock)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void getPatientById() throws Exception {
        //given
        UUID id = UUID.fromString("9bc28702-826b-4d60-b4c5-015f24484d6d");
        Patient patient = new Patient(id, "Juan", "Perez", "grg@gmail.com", "+542616320489");

        User mock = new User(null, "Juan", "Pedrera", "pedrera@gmail.com", "admin", RoleEnum.ADMIN);

        //expectation
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient), Optional.empty());

        //performs
        mockMvc.perform(get("/api/v1/patient/{id}", id)
                        .header("Authorization", "Bearer " + generateTestToken(mock)))
                .andExpect(status().isOk())
                .andDo(print());

        mockMvc.perform(get("/api/v1/patient/{id}", id)
                        .header("Authorization", "Bearer " + generateTestToken(mock)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void createPatient() throws Exception {
        //Given
        Patient patient = new Patient(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489");
        User mock = new User(null, "Juan", "Pedrera", "pedrera@gmail.com", "admin", RoleEnum.ADMIN);

        //expectation (se utliza cuando se espera algo de la BDD)
//        when(userRepository.save(user)).thenReturn(null);

        //perform
        mockMvc.perform(post("/api/v1/patient")
                        .header("Authorization", "Bearer " + generateTestToken(mock))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updatePatient() throws Exception {
        //Given
        UUID id = UUID.randomUUID();
        Patient patient = new Patient(id, "Juan", "Perez", "grg@gmail.com", "+542616320489");

        //expectation
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient), Optional.empty());
//        when(userRepository.save(user)).thenReturn(null);

        //perform
        mockMvc.perform(put("/api/v1/patient/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andDo(print());

        mockMvc.perform(put("/api/v1/patient/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteAllPatients() throws Exception {
        //given
        List<Patient> patients = new ArrayList<>();

        //expectation
//        when(userRepository.findAll()).thenReturn(users);
        doNothing().when(patientRepository).deleteAll();

        //perform
        mockMvc.perform(delete("/api/v1/patient"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deletePatientById() throws Exception {
        //given
        UUID id = UUID.fromString("a0e0a6e5-5b76-4e39-8842-33120204d1d8");
        Patient patient = new Patient(id, "Juan", "Perez", "grg@gmail.com", "+542616320489");

        //expectation
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient), Optional.empty());

        //perform
        mockMvc.perform(delete("/api/v1/patient/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());

        mockMvc.perform(delete("/api/v1/patient/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    private String generateTestToken(User user) {
//        User user = User.builder()
//                .email("gdbarimboim@gmail.com")
//                .build();

        return jwtService.generateToken(user);
    }
}