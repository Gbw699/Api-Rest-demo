package com.example.Servletdemofull.infrastructure.input.rest.controllers;

import com.example.Servletdemofull.infrastructure.input.rest.dtos.UserDto;
import com.example.Servletdemofull.infrastructure.input.rest.mappers.UserMapper;
import com.example.Servletdemofull.infrastructure.output.entity.User;
import com.example.Servletdemofull.infrastructure.output.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
    }


    @Test
    void getAllUsers() throws Exception {
        //given
        List<User> users = new ArrayList<>(
                Arrays.asList(
                        new User(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489"),
                        new User(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489"),
                        new User(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489")
                )
        );

        //expectation
        when(userRepository.findAll()).thenReturn(users);

        //performs
        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getUserById() throws Exception {
        //given
        UUID id = UUID.randomUUID();
        User user = new User(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489");

        //expectation
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        //performs
        mockMvc.perform(get("/api/user/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void createUser() throws Exception {
        //Given
        User user = new User(UUID.randomUUID(), "Juan", "Perez", "grg@gmail.com", "+542616320489");

        //expectation (se utliza cuando se espera algo de la BDD)
//        when(userRepository.save(user)).thenReturn(null);

        //perform
        mockMvc.perform(post("/api/user").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteAllUsers() {
    }

    @Test
    void deleteUserById() {
    }
}