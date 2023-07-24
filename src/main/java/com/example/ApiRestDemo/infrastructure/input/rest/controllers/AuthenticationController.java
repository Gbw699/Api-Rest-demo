package com.example.ApiRestDemo.infrastructure.input.rest.controllers;

import com.example.ApiRestDemo.application.services.RegisterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import com.example.ApiRestDemo.application.services.AuthenticationService;
import com.example.ApiRestDemo.infrastructure.input.rest.dtos.RegisterRequestDto;
import com.example.ApiRestDemo.infrastructure.input.rest.dtos.AuthenticationRequestDto;
import com.example.ApiRestDemo.infrastructure.input.rest.dtos.AuthenticationResponseDto;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final RegisterService registerService;
    private final AuthenticationService authenticateService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody @Valid RegisterRequestDto request
    ) {
        return ResponseEntity.ok(registerService.register(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponseDto> authentication(
            @RequestBody @Valid AuthenticationRequestDto request
    ) {
        return ResponseEntity.ok(authenticateService.authenticate(request));
    }
}
