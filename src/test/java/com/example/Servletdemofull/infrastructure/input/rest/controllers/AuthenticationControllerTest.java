package com.example.Servletdemofull.infrastructure.input.rest.controllers;

import com.example.Servletdemofull.infrastructure.input.rest.dtos.AuthenticationRequestDto;
import com.example.Servletdemofull.infrastructure.input.rest.dtos.RegisterRequestDto;
import com.example.Servletdemofull.infrastructure.output.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//   @Autowired
//   private AuthenticationManager authenticationManager;

    @Test
    void registerTest() throws Exception {
        //given
        RegisterRequestDto requestDto = new RegisterRequestDto("Juan", "Carlos", "jcarlos@gmail.com", "admin");

        //expectation

        //permorms
        mockMvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void authenticationTest() throws Exception {
        //given
        AuthenticationRequestDto requestDto = new AuthenticationRequestDto("jcarlos@gmail.com", "admin");
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        "jcarlos@gmail.com",
//                        "admin"
//                )
//        );

        //expectation
//        when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(user);

        //performs
        mockMvc.perform(post("/api/v1/auth/authentication").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}