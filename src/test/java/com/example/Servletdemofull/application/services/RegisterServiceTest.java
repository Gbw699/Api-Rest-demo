package com.example.Servletdemofull.application.services;

import com.example.Servletdemofull.domain.user.RoleEnum;
import com.example.Servletdemofull.config.JWT.JwtService;
import com.example.Servletdemofull.application.utils.UserExistInDb;
import com.example.Servletdemofull.infrastructure.output.entity.User;
import com.example.Servletdemofull.infrastructure.output.repository.UserRepository;
import com.example.Servletdemofull.infrastructure.input.rest.dtos.RegisterRequestDto;
import com.example.Servletdemofull.infrastructure.input.rest.dtos.AuthenticationResponseDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    private RegisterService registerService;
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserExistInDb userExistInDb;

    @BeforeEach
    void setUp() {
        registerService = new RegisterService(repository, passwordEncoder, jwtService, userExistInDb);
    }

    @Test
    void success() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Carlos", "Carlitos", "carlitos@gmail.com", "admin");

        var user = User.builder()
                .firstname(registerRequestDto.getFirstname())
                .lastname(registerRequestDto.getLastname())
                .email(registerRequestDto.getEmail())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .role(RoleEnum.ADMIN)
                .build();

        AuthenticationResponseDto responseDto = registerService.register(registerRequestDto);

        String expectedToken = jwtService.generateToken(user);
        String actualToken = responseDto.getToken();

        Assertions.assertEquals(expectedToken, actualToken);
    }

    @Test
    void userAlreadyExistTest() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Carlos", "Carlitos", "carlitos@gmail.com", "admin");

        when(userExistInDb.ifUserExist(registerRequestDto.getEmail())).thenReturn(true);

        AuthenticationResponseDto responseDto = registerService.register(registerRequestDto);

        String expectedToken = "User already exist";
        String actualToken = responseDto.getToken();

        Assertions.assertEquals(expectedToken, actualToken);
    }
}