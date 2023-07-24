package com.example.ApiRestDemo.application.services;

import org.springframework.stereotype.Service;

import com.example.ApiRestDemo.config.JWT.JwtService;
import com.example.ApiRestDemo.infrastructure.output.repository.UserRepository;
import com.example.ApiRestDemo.infrastructure.input.rest.dtos.AuthenticationRequestDto;
import com.example.ApiRestDemo.infrastructure.input.rest.dtos.AuthenticationResponseDto;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto requestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword()
                )
        );
        var user = repository.findByEmail(requestDto.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

}
