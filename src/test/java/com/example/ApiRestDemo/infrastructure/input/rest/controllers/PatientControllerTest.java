package com.example.ApiRestDemo.infrastructure.input.rest.controllers;

import com.example.ApiRestDemo.config.JWT.JwtService;
import com.example.ApiRestDemo.domain.user.RoleEnum;
import com.example.ApiRestDemo.infrastructure.output.entity.Patient;
import com.example.ApiRestDemo.infrastructure.output.entity.User;
import com.example.ApiRestDemo.infrastructure.output.repository.PatientRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.restdocs.RestDocumentationExtension;

import static org.springframework.restdocs.http.HttpDocumentation.httpRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    User mock = new User(null, "Juan", "Pedrera", "pedrera@gmail.com", "admin", RoleEnum.ADMIN);

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .snippets()
                        .withDefaults(httpRequest(), httpResponse())
                        .and()
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    @Test
    @WithMockUser
    void getAllPatientsTest() throws Exception {
        //given
        List<Patient> patients = new ArrayList<>(
                Arrays.asList(
                        new Patient(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489"),
                        new Patient(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489"),
                        new Patient(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489")
                )
        );

        List<Patient> patients2 = new ArrayList<>(
                List.of()
        );

        //expectation
        when(patientRepository.findAll()).thenReturn(patients, patients2);

        //performs
        mockMvc.perform(get("/api/v1/patient")
                        .header("Authorization", "Bearer " + generateTestToken(mock)))
                .andExpect(status().isOk())
                .andDo(document("{method-name}"));

        mockMvc.perform(get("/api/v1/patient")
                        .header("Authorization", "Bearer " + generateTestToken(mock)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void getPatientByIdTest() throws Exception {
        //given
        UUID id = UUID.fromString("9bc28702-826b-4d60-b4c5-015f24484d6d");
        Patient patient = new Patient(id, "Juan", "Perez", "grg@gmail.com", "+542616320489");

        //expectation
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient), Optional.empty());

        //performs
        mockMvc.perform(get("/api/v1/patient/{id}", id)
                        .header("Authorization", "Bearer " + generateTestToken(mock)))
                .andExpect(status().isOk())
                .andDo(document("{method-name}"));

        mockMvc.perform(get("/api/v1/patient/{id}", id)
                        .header("Authorization", "Bearer " + generateTestToken(mock)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void createPatientTest() throws Exception {
        //Given
        Patient patient = new Patient(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489");

        //expectation (se utliza cuando se espera algo de la BDD)

        //perform
        mockMvc.perform(post("/api/v1/patient")
                        .header("Authorization", "Bearer " + generateTestToken(mock))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andDo(document("{method-name}"));
    }

    @Test
    @WithMockUser
    void updatePatientTest() throws Exception {
        //Given
        UUID id = UUID.randomUUID();
        Patient patient = new Patient(id, "Juan", "Perez", "grg@gmail.com", "+542616320489");
        Patient patient2 = new Patient(UUID.fromString("f6fe7b6f-c692-4263-b208-e0b3aa0b7e2c"), "Juan", "Perez", "grg@gmail.com", "+542616320489");

        //expectation
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient), Optional.empty());

        //perform
        mockMvc.perform(put("/api/v1/patient/{id}", id)
                        .header("Authorization", "Bearer " + generateTestToken(mock))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andDo(document("{method-name}"));

        mockMvc.perform(put("/api/v1/patient/{id}", id)
                        .header("Authorization", "Bearer " + generateTestToken(mock))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isNotFound())
                .andDo(print());

        mockMvc.perform(put("/api/v1/patient/{id}", id)
                        .header("Authorization", "Bearer " + generateTestToken(mock))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient2)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void deletePatientByIdTest() throws Exception {
        //given
        UUID id = UUID.fromString("a0e0a6e5-5b76-4e39-8842-33120204d1d8");
        Patient patient = new Patient(id, "Juan", "Perez", "grg@gmail.com", "+542616320489");

        //expectation
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient), Optional.empty());

        //perform
        mockMvc.perform(delete("/api/v1/patient/{id}", id)
                        .header("Authorization", "Bearer " + generateTestToken(mock)))
                .andExpect(status().isOk())
                .andDo(document("{method-name}"));

        mockMvc.perform(delete("/api/v1/patient/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void deleteAllPatientsTest() throws Exception {
        //given
        List<Patient> patients = new ArrayList<>(
                Arrays.asList(
                        new Patient(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489"),
                        new Patient(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489"),
                        new Patient(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489")
                )
        );

        List<Patient> patients2 = new ArrayList<>(
                List.of()
        );

        //expectation
        when(patientRepository.findAll()).thenReturn(patients, patients2);

        //perform
        mockMvc.perform(delete("/api/v1/patient")
                        .header("Authorization", "Bearer " + generateTestToken(mock)))
                .andExpect(status().isOk())
                .andDo(document("{method-name}"));

        mockMvc.perform(delete("/api/v1/patient")
                        .header("Authorization", "Bearer " + generateTestToken(mock)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    private String generateTestToken(User user) {
        return jwtService.generateToken(user);
    }
}