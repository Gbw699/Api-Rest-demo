package com.example.Servletdemofull.infrastructure.input.rest.controllers;

import org.springframework.http.ResponseEntity;

import com.example.Servletdemofull.infrastructure.input.services.AuthenticationService;
import com.example.Servletdemofull.infrastructure.input.rest.dtos.RegisterRequestDto;
import com.example.Servletdemofull.infrastructure.input.rest.dtos.AuthenticationRequestDto;
import com.example.Servletdemofull.infrastructure.input.rest.dtos.AuthenticationResponseDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody RegisterRequestDto request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponseDto> authentication(
            @RequestBody AuthenticationRequestDto request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
