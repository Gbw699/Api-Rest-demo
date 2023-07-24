package com.example.ApiRestDemo.application.services;

import com.example.ApiRestDemo.domain.user.RoleEnum;
import com.example.ApiRestDemo.config.JWT.JwtService;
import com.example.ApiRestDemo.infrastructure.output.entity.User;
import com.example.ApiRestDemo.infrastructure.output.repository.UserRepository;
import com.example.ApiRestDemo.infrastructure.input.rest.dtos.AuthenticationRequestDto;
import com.example.ApiRestDemo.infrastructure.input.rest.dtos.AuthenticationResponseDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private AuthenticationService authenticationService;
    @Mock
    private UserRepository repository;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;


    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService(repository, jwtService, authenticationManager);
    }

    @Test
    void success() {
        AuthenticationRequestDto requestDto = new AuthenticationRequestDto("carlitos@gmail.com", "admin");
        User user = new User(1, "Carlos", "Carlitos", "carlitos@gmail.com", "$2a$10$TnJ5gupMxWpLtx71eRVAR.5lw36iUXSIRoc1VnEaanz7I2ordWYo6", RoleEnum.ADMIN);
        var jwtToken = jwtService.generateToken(user);

        when(repository.findByEmail(requestDto.getEmail())).thenReturn(Optional.of(user));

        AuthenticationResponseDto responseDto = authenticationService.authenticate(requestDto);

        Assertions.assertEquals(jwtToken, responseDto.getToken());
    }

    @Test
    void noValuePresent() {
        AuthenticationRequestDto requestDto = new AuthenticationRequestDto("carlitos@gmail.com", "admin");

        when(repository.findByEmail(requestDto.getEmail())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> authenticationService.authenticate(requestDto));

        Assertions.assertEquals("No value present", exception.getMessage());
    }
}